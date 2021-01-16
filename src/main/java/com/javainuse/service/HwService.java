package com.javainuse.service;

import com.javainuse.response.HwResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HwService {
    private final List<String> messageTypes = List.of("1", "2");

    public HwResponse getMessage(String messageType) {
        HwResponse rsp = new HwResponse();
        if (messageTypes.contains(messageType)) {
            rsp.setMessage("Hello world");
            rsp.setMessageCode(100);
        } else {
            throw new RuntimeException("not allowed messageType");
        }
        return rsp;
    }
}
