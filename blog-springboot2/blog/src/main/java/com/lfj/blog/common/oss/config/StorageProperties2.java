//package com.lfj.blog.common.oss.config;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
///**
// * oss 存储配置
// *
// **/
//@Data
//@ConfigurationProperties(prefix = "oss")
//public class StorageProperties2 {
//
//	private final Netease netease = new Netease();
//	private final Qiniu qiniu = new Qiniu();
//	private final Local local = new Local();
//	private final Ali ali = new Ali();
//	private int type = 1;
//
//
//	public StorageProperties2() {
//
//	}
//
//	/**
//	 * 网易云
//	 */
//	public static class Netease {
//		private String accessKey;
//		private String secretKey;
//		private String endpoint;
//		private String bucket;
//
//		public String getAccessKey() {
//			return accessKey;
//		}
//
//		public void setAccessKey(String accessKey) {
//			this.accessKey = accessKey;
//		}
//
//		public String getSecretKey() {
//			return secretKey;
//		}
//
//		public void setSecretKey(String secretKey) {
//			this.secretKey = secretKey;
//		}
//
//		public String getEndpoint() {
//			return endpoint;
//		}
//
//		public void setEndpoint(String endpoint) {
//			this.endpoint = endpoint;
//		}
//
//		public String getBucket() {
//			return bucket;
//		}
//
//		public void setBucket(String bucket) {
//			this.bucket = bucket;
//		}
//	}
//
//	/**
//	 * 七牛
//	 */
//	public static class Qiniu {
//		private String accessKey;
//		private String secretKey;
//		private String bucket;
//		private String domain;
//		private String region;
//
//		public String getAccessKey() {
//			return accessKey;
//		}
//
//		public void setAccessKey(String accessKey) {
//			this.accessKey = accessKey;
//		}
//
//		public String getSecretKey() {
//			return secretKey;
//		}
//
//		public void setSecretKey(String secretKey) {
//			this.secretKey = secretKey;
//		}
//
//		public String getBucket() {
//			return bucket;
//		}
//
//		public void setBucket(String bucket) {
//			this.bucket = bucket;
//		}
//
//		public String getDomain() {
//			return domain;
//		}
//
//		public void setDomain(String domain) {
//			this.domain = domain;
//		}
//
//		public String getRegion() {
//			return region;
//		}
//
//		public void setRegion(String region) {
//			this.region = region;
//		}
//	}
//
//	/**
//	 * 本地
//	 */
//	public static class Local {
//		private String path = "/var/blog/";
//		private String proxy = "http://localhost:8080/resources/";
//
//		public String getPath() {
//			return path;
//		}
//
//		public void setPath(String path) {
//			this.path = path;
//		}
//
//		public String getProxy() {
//			return proxy;
//		}
//
//		public void setProxy(String proxy) {
//			this.proxy = proxy;
//		}
//	}
//
//	public static class Ali {
//		private String accessKeyId;
//		private String accessKeyIdSecret;
//		private String bucket;
//		private String endpoint;
//
//		public String getAccessKeyId() {
//			return accessKeyId;
//		}
//
//		public void setAccessKeyId(String accessKeyId) {
//			this.accessKeyId = accessKeyId;
//		}
//
//		public String getAccessKeyIdSecret() {
//			return accessKeyIdSecret;
//		}
//
//		public void setAccessKeyIdSecret(String accessKeyIdSecret) {
//			this.accessKeyIdSecret = accessKeyIdSecret;
//		}
//
//		public String getBucket() {
//			return bucket;
//		}
//
//		public void setBucket(String bucket) {
//			this.bucket = bucket;
//		}
//
//		public String getEndpoint() {
//			return endpoint;
//		}
//
//		public void setEndpoint(String endpoint) {
//			this.endpoint = endpoint;
//		}
//	}
//}
