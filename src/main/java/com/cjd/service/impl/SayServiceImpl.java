package com.cjd.service.impl;

import com.cjd.pojo.Say;
import com.cjd.mapper.SayMapper;
import com.cjd.service.SayService;
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
public class SayServiceImpl extends ServiceImpl<SayMapper, Say> implements SayService {

}
