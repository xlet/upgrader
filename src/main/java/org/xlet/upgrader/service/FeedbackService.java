package org.xlet.upgrader.service;

import org.xlet.upgrader.domain.Feedback;
import org.xlet.upgrader.vo.dashboard.FeedbackDTO;

import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-10-9 下午5:45
 * Summary:
 */
public interface FeedbackService {

    public void save(Feedback feedback);

    List<FeedbackDTO> listAll();

}
