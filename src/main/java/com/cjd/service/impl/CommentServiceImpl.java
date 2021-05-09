package com.cjd.service.impl;

import com.cjd.pojo.Comment;
import com.cjd.mapper.CommentMapper;
import com.cjd.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjd
 * @since 2021-04-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
