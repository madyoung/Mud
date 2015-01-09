/**
 * 
 */
package com.mud.server;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mud.exception.IdGenException;
import com.mud.game.PlayerManager;
import com.mud.util.LogUtil;
import com.mud.util.PropertiesFileUtil;
import com.mud.util.SecurityUtil;
import com.mud.vo.UseridToWxidVo;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * @author chenzhiwei
 * @since 2014-12-2 下午2:56:50
 */
public class WXServer {
	private static final Logger log = LoggerFactory.getLogger("wxserver");
	private static final String LOG_ERROR_MESSAGE = "WXServer handle exception.";
	private static final String LOG_EMAIL_SQL_MESSAGE = "Mud Server SQLException";
	private static final String LOG_EMAIL_IDGEN_MESSAGE = "Mud Server IdGenException";
	private static final String LOG_EMAIL_EXCEPTION_MESSAGE = "Mud Server Exception";
	private static final String REPLY_SERVER_PROBLEM = "The server may have some problems, please try again later.";
	private static final String REPLY_FORMAT = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[%4$s]]></Content></xml>";

	public boolean verifyUrl(String signature, String timestamp, String nonce) {
		String[] strings = new String[] { WXConstants.TOKEN, timestamp, nonce };
		Arrays.sort(strings);

		String sortedString = strings[0] + strings[1] + strings[2];
		String sha1String = SecurityUtil.SHA1(sortedString);
		if (sha1String.equals(signature)) {
			return true;
		}
		return false;
	}

	static class WXConstants {
		static final String PROPERTIES_FILE = "wxConfig.properties";

		static String APP_ID;
		static String ENCODING_AES_KEY;
		static String TOKEN;
		static String APP_SECRET;
		static String WX_USER_NAME;

	}

	public WXServer() {
		Properties properties = PropertiesFileUtil.getPropertiesFromResource(WXConstants.PROPERTIES_FILE);
		WXConstants.APP_ID = properties.getProperty("appId");
		WXConstants.ENCODING_AES_KEY = properties.getProperty("encodingAESKey");
		WXConstants.TOKEN = properties.getProperty("token");
		WXConstants.APP_SECRET = properties.getProperty("appSecret");
		WXConstants.WX_USER_NAME = properties.getProperty("wxUserName");
	}

	/**
	 * @param message
	 * @throws AesException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public String handle(String message, String msgSignature, String timeStamp, String nonce) throws AesException, ParserConfigurationException, SAXException, IOException {
		String requestMessage = null;
		String replyMessage = null;
		try {
			WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(
					WXConstants.TOKEN, WXConstants.ENCODING_AES_KEY, WXConstants.APP_ID);

			requestMessage = wxBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, message);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(requestMessage);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("FromUserName");
			NodeList nodelist2 = root.getElementsByTagName("Content");
			NodeList nodelist3 = root.getElementsByTagName("MsgType");
			String userOpenID = nodelist1.item(0).getTextContent();
			String msgType = nodelist3.item(0).getTextContent();
			String replyContent = "";
			try {
				if ("event".equals(msgType)) {
					NodeList nodelist4 = root.getElementsByTagName("Event");
					String event = nodelist4.item(0).getTextContent();
					if ("subscribe".equals(event)) {// bind weixin id
						ServerBo.getInstance().getIdGenServer().generateUseridByWxid(userOpenID);
						// set subscribe message
						replyContent = "Welcome to 囧侦探FC";
					} else if ("unsubscribe".equals(event)) {// unbind weixin id
						ServerBo.getInstance().getIdGenServer().unbindUseridByWxid(userOpenID);
						replyContent = "Why?!";
					}
				} else if ("text".equals(msgType)) { // handle text message
					UseridToWxidVo useridToWxidVo = ServerBo.getInstance().getIdGenServer().queryUseridByWxid(userOpenID);
					if (useridToWxidVo == null) {
						useridToWxidVo = ServerBo.getInstance().getIdGenServer().generateUseridByWxid(userOpenID);
					}
					String content = nodelist2.item(0).getTextContent();
					replyContent = handleText(useridToWxidVo.getUserid(), content);
				}
			} catch (SQLException e) {
				LogUtil.email(LOG_EMAIL_SQL_MESSAGE, LOG_EMAIL_SQL_MESSAGE, e);
				replyContent = REPLY_SERVER_PROBLEM;
				log.error(LOG_ERROR_MESSAGE, e);
			} catch (IdGenException e) {
				LogUtil.email(LOG_EMAIL_IDGEN_MESSAGE, LOG_EMAIL_IDGEN_MESSAGE, e);
				replyContent = REPLY_SERVER_PROBLEM;
				log.error(LOG_ERROR_MESSAGE, e);
			}catch (Exception e) {
				LogUtil.email(LOG_EMAIL_EXCEPTION_MESSAGE, LOG_EMAIL_EXCEPTION_MESSAGE, e);
				replyContent = REPLY_SERVER_PROBLEM;
				log.error(LOG_ERROR_MESSAGE, e);
			}
			replyMessage = String.format(REPLY_FORMAT, userOpenID, WXConstants.WX_USER_NAME, timeStamp, replyContent);
			String encryptedMsg = wxBizMsgCrypt.encryptMsg(replyMessage, timeStamp, nonce);
			return encryptedMsg;
		} finally {
			log.info("request={},reply={}", new Object[] { requestMessage, replyMessage });
		}
	}

	private String handleText(long userid, String content) {
		String replyContent = "";
		replyContent = PlayerManager.getPlayer(userid).play(content);
		return replyContent;
	}
}
