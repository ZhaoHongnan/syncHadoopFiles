package renren_inc.syncAdLogToHadoop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.permission.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import parser.BaiduParseFile;
import parser.TanxParseFile;

/**sync logs of the ad engine from the local to Hadoop 
 * 
 * @author hongnan.zhao
 *
 */
public class Schedule {
	public static Logger logger = Logger.getLogger(Schedule.class);
	
	private String startTime;
	
	private String endTime;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
	
	private long latency = 60 * 1000 * 4;// 4 min latency.
	
	private List<String> srcPrefixes;
	
	private List<String> srcLocalFileTypes;
	
	private List<String> desPrefixes;
	
	private List<String> desHdfsFileTypes;
	
	private String hdfsDataType;
	
	private String serverName;
	
	private String srcBasePath;
	
	private String desBasetPath;
	
	private String SEP = "/";
	
	private String _SEP = "_";
	
	private String suffix = ".log.gz";
	
	private Configuration conf;
	
	private String nameNode;
	
	public void execute(){
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		try{
			if(startTime == null){
				startCal.setTime(df.parse(df.format(new Date())));
			}else{
				startCal.setTime(df.parse(startTime));
			}
			if(endTime == null){
				endCal.setTime(df.parse("2100_01_01_00_00"));
			}else{
				endCal.setTime(df.parse(endTime));
			}
		}catch(ParseException pe){
			logger.error("Start or End time is not valid.");
			throw new RuntimeException("Start or End time is not valid.", pe);
		}
		logger.info("starting while loop!!!");
		while(!startCal.after(endCal)){
			//postpone in 4 minutes later 
			long threshold = System.currentTimeMillis() - latency;
			if(startCal.getTimeInMillis()>threshold){
				try{
					Thread.sleep(30 * 1000l);//30 seconds
					logger.info("Thread is sleeping 30 seconds, please wait!");
				}catch (InterruptedException ie) {
					logger.error("The running thread is Interrupted.");
					throw new RuntimeException("The running thread is Interrupted.", ie);
				}
				continue;
			}
			int count = srcPrefixes.size();
			for(int i=0; i<count; ++i){
				//The local operations
				//local source file
				String[] srcFile = generateLocalSourcePath(srcBasePath, srcLocalFileTypes.get(i), startCal, srcPrefixes.get(i));
				logger.debug("Local source file is" + srcFile[0] +srcFile[1]);
				//local destination file
				String[] desFile = generateLocalDestinationPath(desBasetPath, startCal, desPrefixes.get(i));
				logger.debug("Local destination file is" + desFile[0] + desFile[1]);
				Path desDirectory = Paths.get(desFile[0]);
				if(!Files.isDirectory(desDirectory)){
					try {
						logger.debug("Creating local directory" + desDirectory.toString());
						Files.createDirectories(desDirectory);
					} catch (IOException e) {
						logger.error("creating directory error");
					}
				}
				logger.debug("Creating local directory finished");
				
				
				logger.debug("Copying local files start");
				if (desPrefixes.get(i).equals("baidu_req")){
					logger.debug("Parsing Baidu logs");
					BaiduParseFile bpf = new BaiduParseFile();
					bpf.parse(srcFile[0] + srcFile[1], desFile[0] + desFile[1]);
				}else if(desPrefixes.get(i).equals("tanx_req")){
					logger.debug("Parsing Tanx logs");
					TanxParseFile tpf = new TanxParseFile();
					tpf.parse(srcFile[0] + srcFile[1], desFile[0] + desFile[1]);
				}else{
					//Initializing source and destination path
					Path srcPath = FileSystems.getDefault().getPath(srcFile[0], srcFile[1]);
					Path desPath = FileSystems.getDefault().getPath(desFile[0], desFile[1]);
					try {
						//copy one file on the local
						Files.copy(srcPath, desPath, StandardCopyOption.REPLACE_EXISTING);
						logger.debug("Copying local files from " + srcPath.toString() + "to" + desPath.toString());
					} catch (IOException e) {
						logger.error("copy local files error from " + srcPath.toString() + "to" + desPath.toString());
					}
				}
				logger.debug("Copying local files finished");
				
				//Hadoop operations
				File desLocalFile = new File(desFile[0] + desFile[1]);
				String desHdfsPathStr = generateHdfsPath(startCal, desHdfsFileTypes.get(i));
				logger.debug("HDFS destination path is " + desHdfsPathStr);
				//Initializing the HDFS path
				logger.debug("Intializing the HDFS path");
				org.apache.hadoop.fs.Path desHdfsPath = 
						new org.apache.hadoop.fs.Path(desHdfsPathStr);
				org.apache.hadoop.fs.FileSystem desHdfs = null;
				try {
					logger.debug("Intializing the HDFS system");
					conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
					conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
					desHdfs = org.apache.hadoop.fs.FileSystem.get(URI.create(nameNode), conf);
				} catch (IOException e) {
					logger.error("Intializing the HDFS system error");
					e.printStackTrace();
				}
				logger.debug("Intializing the HDFS system finished");
				try {
					logger.debug("Copying one local file to HDFS start");
					//set the permission of the hadoop files
					FsPermission fsPermission = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL);
					desHdfs.mkdirs(desHdfsPath, fsPermission);
					//FileUtil.setPermission(desLocalFile, fsPermission);
					//copy one file from the local to the hadoop
					FileUtil.copy(desLocalFile, desHdfs, desHdfsPath, true, conf);
					logger.info(desHdfsPath.toString() + SEP +desFile[1]);
					logger.debug("Copying one local file to HDFS end");
				} catch (Exception e) {
					logger.error("Copying one local file to HDFS error: " + desHdfsPath);
					e.printStackTrace();
				}
			}//for
			logger.debug("Pre data is " + startCal.toString());
			startCal.add(Calendar.MINUTE, 1);
			logger.debug("Next data is " + startCal.toString());
		}//while
	}
	
	public String generateHdfsPath(Calendar cal, String fileType){
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = convertSingleNumber(String.valueOf(cal.get(Calendar.MONTH) + 1));
		String day = convertSingleNumber(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		String hour = convertSingleNumber(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
		String path = hdfsDataType + SEP + "log_in" + SEP + fileType + SEP + year + SEP 
				+ month + SEP + day + SEP + hour + SEP;
		return path;
	}
	
	public String[] generateLocalSourcePath(String path, String fileType,Calendar cal, String filePrefix){
		String[] strs = new String[2];
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = convertSingleNumber(String.valueOf(cal.get(Calendar.MONTH) + 1));
		String day = convertSingleNumber(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		String hour = convertSingleNumber(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
		String minute = convertSingleNumber(String.valueOf(cal.get(Calendar.MINUTE)));
		strs[0] = path + SEP + fileType + SEP + year + SEP + month + SEP + day + SEP + hour + SEP;
		strs[1] = filePrefix + _SEP + "val" + _SEP + year + _SEP + month + _SEP + day + _SEP
				+ hour + _SEP + minute + suffix;
		return strs;
	}
	
	public String[] generateLocalDestinationPath(String path, Calendar cal, String filePrefix){
		String[] strs = new String[2];
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = convertSingleNumber(String.valueOf(cal.get(Calendar.MONTH) + 1));
		String day = convertSingleNumber(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		String hour = convertSingleNumber(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
		String minute = convertSingleNumber(String.valueOf(cal.get(Calendar.MINUTE)));
		strs[0] = path + SEP + year + SEP + month + SEP + day + SEP + hour + SEP;
		strs[1] = getServerName() + _SEP + filePrefix + _SEP + "val" + _SEP + year + _SEP + month + _SEP + day + _SEP
				+ hour + _SEP + minute + suffix;
		return strs;
	}
	
	public String convertSingleNumber(String str) {
		if (str.length() == 1) {
			return "0" + str;
		} else {
			return str;
		}
	}
	
	public static void main(String[] args){
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/syncAdLogToHadoop.xml");
		ApplicationContext ctx = new ClassPathXmlApplicationContext("syncAdLogToHadoop.xml");
		Schedule schedule = (Schedule) ctx.getBean("schedule");
		//only initializing the Start time
		if(args.length == 1){
			logger.debug("args parameters is ONE");
			schedule.setStartTime(args[0]);
		}
		//initializing the Start and End time
		if(args.length == 2){
			logger.debug("args parameters is TWO");
			schedule.setStartTime(args[0]);
			schedule.setEndTime(args[1]);
		}
		//main procedure
		schedule.execute();
	}

	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<String> getSrcPrefixes() {
		return srcPrefixes;
	}

	public void setSrcPrefixes(List<String> srcPrefixes) {
		this.srcPrefixes = srcPrefixes;
	}

	public List<String> getDesPrefixes() {
		return desPrefixes;
	}

	public void setDesPrefixes(List<String> desPrefixes) {
		this.desPrefixes = desPrefixes;
	}

	public List<String> getDesHdfsFileTypes() {
		return desHdfsFileTypes;
	}

	public void setDesHdfsFileTypes(List<String> desHdfsFileTypes) {
		this.desHdfsFileTypes = desHdfsFileTypes;
	}

	public String getHdfsDataType() {
		return hdfsDataType;
	}

	public void setHdfsDataType(String hdfsDataType) {
		this.hdfsDataType = hdfsDataType;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getSrcBasePath() {
		return srcBasePath;
	}

	public void setSrcBasePath(String srcBasePath) {
		this.srcBasePath = srcBasePath;
	}

	public String getDesBasetPath() {
		return desBasetPath;
	}

	public void setDesBasetPath(String desBasetPath) {
		this.desBasetPath = desBasetPath;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public List<String> getSrcLocalFileTypes() {
		return srcLocalFileTypes;
	}

	public void setSrcLocalFileTypes(List<String> srcLocalFileTypes) {
		this.srcLocalFileTypes = srcLocalFileTypes;
	}

	public String getNameNode() {
		return nameNode;
	}

	public void setNameNode(String nameNode) {
		this.nameNode = nameNode;
	}
	
}
