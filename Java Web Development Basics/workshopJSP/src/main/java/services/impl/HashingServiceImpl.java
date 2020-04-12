package services.impl;

import services.HashingService;

public class HashingServiceImpl implements HashingService {

    @Override
    public String hash(String value) {
        int hash = 7;
        for (int i = 0; i < value.length(); i++) {
            hash = hash*31 + value.charAt(i);
        }
        return value + hash;
    }
}
