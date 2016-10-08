package parser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.alibaba.fastjson.JSON;

public class BaiduParseFile {
	private long head_index = 0;

	public String getHeadString(InputStream is, boolean[] isNormalFile) {
		byte[] temp = new byte[1];
		ArrayList<Byte> result = new ArrayList<Byte>(512);
		int doubleLine = 0;
		boolean isLine = false;
		boolean isJsonHave = false;
		try {
			while (is.read(temp) > 0) {
				if (temp[0] == 124) {   //"|"
					result.add(temp[0]);
					if (isLine == true) {
						doubleLine++;
						if (doubleLine >= 5) {
							if (isJsonHave == true) {// 这个是为了剔除 JSON 中含有 || 的情况
								continue;
							}
							head_index++;
							return getStringFromBytes(result);
						}
						isLine = false;
					} else {
						isLine = true;
					}
				} else if (temp[0] == 10 && result.size() > 0) {//"\n" 已经读到下一行还没有结构化数据则认为是非结构化数据
					isNormalFile[0] = true;
					head_index++;
					return getStringFromBytes(result);
				} else if (temp[0] != 13 && temp[0] != 10) {//"\r" 清除无用的字符 \r \n等
					result.add(temp[0]);
					if (temp[0] == 123 && doubleLine >= 4) {    //"}"
						isJsonHave = true;
					}
					isLine = false;
				} else {
					isLine = false;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public String getStringFromBytes(ArrayList<Byte> list) {
		byte[] result = new byte[list.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = list.get(i);
		}
		return new String(result);
	}

	public BaiduRealtimeBidding.BidRequest getBidRequest(InputStream is,
			int length) {
		byte[] result = new byte[length];
		int temp = 0;
		try {
			temp = is.read(result);
			while (temp < length) {
				int temp2 = is.read(result, temp, length - temp);
				if (temp2 < 0) {
					return null;
				}
				temp += temp2;
			}
			BaiduRealtimeBidding.BidRequest bidRequest = BaiduRealtimeBidding.BidRequest
					.parseFrom(result);
			return bidRequest;
		} catch (IOException e) {
			System.out.println("Attemp read:" + length + "bytes<-->Real read:"
					+ temp + "bytes");
			System.out.println("The orgin data is:");
			System.out.println(new String(result));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BaiduRealtimeBidding.BidResponse getBidResponse(InputStream is,
			int length) {
		byte[] result = new byte[length];
		try {
			int temp = is.read(result);
			if (temp < length) {
				return null;
			}
			BaiduRealtimeBidding.BidResponse bidResponse = BaiduRealtimeBidding.BidResponse
					.parseFrom(result);
			return bidResponse;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void readNextLine(InputStream is) {
		try {
			int temp = is.read();
			while (temp > 0 && temp != 10) {
				temp = is.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parse(InputStream is, OutputStream os) {
		String head = null;
		String[] heads = null;
		boolean[] isNormalFile = new boolean[1];
		isNormalFile[0] = false;
		while (true) {
			isNormalFile[0] = false;
			head = getHeadString(is, isNormalFile);

			while (head != null && !head.trim().startsWith("bd")) {
				System.err.println("Some thing wrong when read head:" + head);
				System.err.println("This occured at head index:" + head_index);
				System.err.println("Attemp to read next line");
				isNormalFile[0] = false;
				head = getHeadString(is, isNormalFile);
			}
			if (head == null) {
				break;
			}
			if (isNormalFile[0] == true) {// 表示是已经解析好的文件
				writeOneToDest(os, head.getBytes());
				writeOneToDest(os, "\n".getBytes());
				continue;
			}
			heads = head.split("\\|\\|");
			int len = 0;
			if (heads.length >= 5) {
				try {
					len = Integer.parseInt(heads[4]);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			} else {
				System.err.println("Some thing wrong when aplit head:" + head);
				System.err.println("This occured at head index:" + head_index);
				System.err.println("The head must with format");
				// break;
				continue;
			}
			if (len > 0) {
				BaiduRealtimeBidding.BidRequest bidRequest = getBidRequest(is,
						len);
				if (bidRequest != null) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < heads.length; i++) {
						if (i == 4) {
							continue;
						}
						sb.append(heads[i]);
						sb.append("||");
					}
					sb.append(JSON.toJSONString(BaiduRequest
							.getBaiduRequestFromBidRequest(bidRequest)));
					writeOneToDest(os, sb.toString().getBytes());
					writeOneToDest(os, "\n".getBytes());
				}
			}
		}
	}


	public void writeOneToDest(OutputStream os, byte[] result) {
		try {
			os.write(result);
			// os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parse(String inputString, String outputStirng) {
		InputStream is = null;
		OutputStream os = null;
		File fis = new File(inputString);
		File fos = new File(outputStirng);
		try {
			if (inputString != null && inputString.endsWith(".gz")) {
				is = new BufferedInputStream(new GZIPInputStream(
						new FileInputStream(fis)));
			} else {
				is = new BufferedInputStream(new FileInputStream(fis));
			}
			if (outputStirng != null && outputStirng.endsWith(".gz")) {
				os = new BufferedOutputStream(new GZIPOutputStream(
						new FileOutputStream(fos)));
			} else {
				os = new BufferedOutputStream(new FileOutputStream(fos));
			}
			parse(is, os);
			os.flush();
			System.out.println("Parse " + inputString + " to " + outputStirng
					+ " success");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
