package com.cjd.service.impl;

import com.cjd.pojo.Question;
import com.cjd.mapper.QuestionMapper;
import com.cjd.service.QuestionService;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
