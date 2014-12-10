package org.xlet.upgrader.service.impl;

import org.xlet.upgrader.domain.Feedback;
import org.xlet.upgrader.repository.FeedbackRepository;
import org.xlet.upgrader.service.FeedbackService;
import org.xlet.upgrader.vo.dashboard.FeedbackDTO;
import com.google.common.collect.Lists;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-9 下午5:46
 * Summary:
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private Mapper dozer;
    @Override
    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }


    @Override
    public List<FeedbackDTO> listAll() {
        Iterator<Feedback> iterator = feedbackRepository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "createAt"))).iterator();
        List<FeedbackDTO> dtoList = Lists.newArrayList();
        while(iterator.hasNext()){
            Feedback feedback = iterator.next();
            dtoList.add(dozer.map(feedback, FeedbackDTO.class));
        }
        return dtoList;
    }

}
