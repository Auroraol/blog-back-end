package com.lfj.blog.common.oss.service;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.lfj.blog.common.oss.AbstractStorage;
import com.lfj.blog.common.oss.PageStorageObject;
import com.lfj.blog.common.oss.config.StorageProperties;
import com.lfj.blog.common.response.enums.ResponseCodeEnum;
import com.lfj.blog.exception.ApiException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 阿里云 存储
 **/
@Log4j2
public class AliStorage extends AbstractStorage {

	/*私有
	 * 只允许自己读写操作，其他用户没有权限
	 */
	CannedAccessControlList acl_private = CannedAccessControlList.Private;
	/*公共读写
	 * 允许自己和其他用户读写操作
	 */
	CannedAccessControlList acl_pub_readwrite = CannedAccessControlList.PublicReadWrite;
	/*公共读
	 * 只允许自己进行写操作，但是允许自己及其他用户进行读操作
	 */
	CannedAccessControlList acl_pub_red = CannedAccessControlList.PublicRead;

	private OSSClient client;
	private String endpoint;
	private String bucket;

	public AliStorage(StorageProperties.Ali ali) {
		this.bucket = ali.getBucket();
		this.endpoint = ali.getEndpoint();
		client = new OSSClient(this.endpoint, ali.getAccessKeyId(), ali.getAccessKeyIdSecret());
		createBucket(client, bucket, acl_pub_readwrite);  //Bucket ACL 设置为公共读写
	}

	public static void createBucket(OSSClient client, String bucketName,
									CannedAccessControlList acl) {
		/* 通过一个Bucket对象来创建 */
		CreateBucketRequest bucketObj = new CreateBucketRequest(null);// 构造函数入参为Bucket名称，可以为空
		bucketObj.setBucketName(bucketName);// 设置bucketObj名称
		bucketObj.setCannedACL(acl);// 设置bucketObj访问权限acl
		client.createBucket(bucketObj);// 创建Bucket

	}


	/**
	 * 文件上传
	 *
	 * @param bytes       文件字节数组
	 * @param path        文件路径
	 * @param contentType 文件类型
	 * @return http地址
	 */
	@Override
	public String upload(byte[] bytes, String path, String contentType) {
		return upload(new ByteArrayInputStream(bytes), path, contentType);
	}

	/**
	 * 文件上传
	 *
	 * @param inputStream 字节流
	 * @param path        文件路径
	 * @param contentType 文件类型
	 * @return http地址
	 */
	@Override
	public String upload(InputStream inputStream, String path, String contentType) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);

		client.putObject(bucket, path, inputStream, metadata);
		return "https://" + bucket + "." + endpoint + "/" + path;
	}

	/**
	 * 删除文件
	 *
	 * @param fullPath 文件完整路径
	 * @return 是否删除成功
	 */
	@Override
	public boolean delete(String fullPath) {
		if (StringUtils.isBlank(fullPath)) {
			return false;
		}
		try {
			client.deleteObject(bucket, getFileNameFromFullPath(fullPath));
		} catch (Exception ex) {
			log.error("删除文件失败:{0}", ex);
			throw new ApiException(ResponseCodeEnum.SYSTEM_ERROR.getCode(), "删除文件失败");
		}
		return true;
	}

	/**
	 * 分页获取文件对象列表
	 *
	 * @param nextMarker 下一个marker
	 * @param size
	 * @return
	 */
	@Override
	public PageStorageObject page(String nextMarker, int size) {
		return new PageStorageObject();
	}
}
