package com.cjd.service.impl;

import com.cjd.pojo.Blog;
import com.cjd.mapper.BlogMapper;
import com.cjd.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
