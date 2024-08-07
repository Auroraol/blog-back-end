package com.lfj.blog.controller.article;


import com.lfj.blog.common.response.ApiResponseResult;
import com.lfj.blog.service.IArticleReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文章回复表 前端控制器
 *
 */
@RestController
@RequestMapping("/article/reply")
@Api(tags = "文章评论回复服务", value = "/article/reply")
public class ArticleReplyController {

	@Autowired
	private IArticleReplyService articleReplyService;

	@PostMapping("/add")
	@ApiOperation(value = "新增文章评论回复", notes = "需要accessToken")
	public ApiResponseResult add(@ApiParam(value = "文章id") @NotNull(message = "文章id不能为空") @RequestParam(value = "articleId") Integer articleId,
								 @ApiParam(value = "评论id") @NotNull(message = "评论id不能为空") @RequestParam(value = "commentId") Integer commentId,
								 @ApiParam(value = "被回复者id") @NotNull(message = "被回复者id不能为空") @RequestParam(value = "toUserId") Integer toUserId,
								 @ApiParam(value = "回复内容") @NotBlank(message = "回复内容不能为空") @RequestParam(value = "content") String content) {
		articleReplyService.add(articleId, commentId, toUserId, content);
		articleReplyService.asyncSendMail(articleId, toUserId, content);  //发送邮件
		return ApiResponseResult.success();
	}

	@DeleteMapping("/delete")
	@ApiOperation(value = "删除回复", notes = "逻辑删除，需要accessToken,管理员或回复者可删除")
	public ApiResponseResult delete(@ApiParam(value = "回复id") @NotNull(message = "回复id不能为空") @RequestParam(value = "replyId") Integer replyId) {
		articleReplyService.delete(replyId);
		return ApiResponseResult.success();
	}
}
