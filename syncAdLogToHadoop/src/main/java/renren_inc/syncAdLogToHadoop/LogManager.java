package renren_inc.syncAdLogToHadoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.log4j.Logger;

public class LogManager {
	public static Logger logger = Logger.getLogger(LogManager.class);
	
	private FileOutputStream fos;
	
	private String filePath;
	
	private String SEP_verticalLineForRead = "\\|";
	
	private String nameNode;
	
	private Configuration conf;
	
	public void init() throws LogManagerException{
		try {
			fos = new FileOutputStream(filePath, false);
		} catch (FileNotFoundException e) {
			logger.error("Initializing FileOutputStream error.");
			throw new LogManagerException();
		}
	}
	
	public void writeNotPutLog(String msg) throws LogManagerException{
		try {
			fos.write((msg + "\n").getBytes());
		} catch (IOException e) {
			logger.error("Writing recover log error.");
			throw new LogManagerException();
		} finally{
			try {
				fos.flush();
			} catch (IOException e) {
				throw new LogManagerException();
			}
		}
	}
	
	public void doRecover() throws LogManagerException{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));		
			String line = null;
			while((line = br.readLine()) != null){
				try{
					String[] strs = line.split(SEP_verticalLineForRead);
					File desLocalFile = new File(strs[0]);
					org.apache.hadoop.fs.Path desHdfsPath =  new org.apache.hadoop.fs.Path(strs[1]);
					org.apache.hadoop.fs.FileSystem desHdfs = org.apache.hadoop.fs.FileSystem.get(URI.create(nameNode), conf);
					FsPermission fsPermission = new FsPermission(FsAction.ALL, FsAction.ALL, FsAction.ALL);
					desHdfs.mkdirs(desHdfsPath, fsPermission);
					FileUtil.copy(desLocalFile, desHdfs, desHdfsPath, true, conf);
				}catch(IOException e){
					logger.info("Hadoop file exists or local file not exists");
					continue;
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException occurred in Reading recover log");
			throw new LogManagerException();
		} catch (IOException e) {
			logger.error("IOException occurred in Reading recover log.");
			throw new LogManagerException();		
		} finally{
			try {
				br.close();
				fos.write("".getBytes());
			} catch (IOException e) {
				throw new LogManagerException();
			}
		}
	}
	
	public void cleanRecoverLog() throws LogManagerException{
		try {
			fos.close();
			fos = new FileOutputStream(filePath, true);
		} catch (IOException e) {
			logger.error("Cleaning recover log error.");
			throw new LogManagerException();
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getNameNode() {
		return nameNode;
	}

	public void setNameNode(String nameNode) {
		this.nameNode = nameNode;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}
	
}
