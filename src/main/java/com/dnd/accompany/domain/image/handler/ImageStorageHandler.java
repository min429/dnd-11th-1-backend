package com.dnd.accompany.domain.image.handler;

import com.dnd.accompany.domain.image.dto.ImageResponse;

import java.io.File;

public interface ImageStorageHandler {

    ImageResponse upload(File file);

    void remove(String key);

    boolean doesObjectExists(String key);
}
