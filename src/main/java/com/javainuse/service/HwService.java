package com.javainuse.service;

import com.javainuse.response.HwResponse;
import exception.RTBusinessException;
import exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HwService {
    private final List<String> messageTypes = List.of("1", "2");

    public HwResponse getMessage(String messageType) throws ValidationException {
        HwResponse rsp = new HwResponse();
        if (messageTypes.contains(messageType)) {
            rsp.setMessage("Hello world");
            rsp.setMessageCode(100);
        } else if (messageType.equals("5")) {
            List<String> list = List.of("validation statement1", "validation statement2");
            throw new ValidationException(list);
        } else {
            throw new RTBusinessException("not allowed messageType");
        }
        return rsp;
    }
}
