package com.juns.wechat.chat.ui;

import com.juns.wechat.chat.bean.MessageBean;
import com.juns.wechat.chat.bean.OfflineVideoMsg;
import com.juns.wechat.chat.bean.PictureMsg;
import com.juns.wechat.database.dao.DbDataEvent;
import com.juns.wechat.database.dao.MessageDao;
import com.style.net.image.FileDownloadCallback;

/**
 * Created by xiajun on 2017/4/7.
 */

public class OfflineVideoDownloadCallback extends FileDownloadCallback {
    private MessageBean messageBean;
    private int progress = 0;
    OfflineVideoMsg pictureMsg;

    public OfflineVideoDownloadCallback(MessageBean messageBean) {
        this.messageBean = messageBean;
        pictureMsg = (OfflineVideoMsg) messageBean.getMsgObj();
    }

    @Override
    public void inProgress(int fileSize, int progress, int percent) {
        this.progress = percent;
        if (percent % 10 == 0)//不要更新太频繁
            updateMessage(percent);
    }

    @Override
    public void complete(String filePath) {
        progress = 100;
        updateMessage(progress);
    }

    private void updateMessage(int progress) {
        pictureMsg.progress = progress;
        messageBean.setMsg(pictureMsg.toJson());
        MessageDao.getInstance().postDataChangedEvent(DbDataEvent.UPDATE, messageBean);
    }
}