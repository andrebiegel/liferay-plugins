package io.github.andrebiegel.backgroudtask;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;

import org.osgi.service.component.annotations.Component;

/**
 * 
 * 
 * @author André Biegel
 */
@Component(immediate = true, service = BackgroundTask.class)
public class MyBackgroundtask implements BackgroundTask {

    @Override
    public void addAttachment(long userId, String fileName, File file) throws PortalException {

    }

    @Override
    public void addAttachment(long userId, String fileName, InputStream inputStream) throws PortalException {

    }

    @Override
    public Folder addAttachmentsFolder() throws PortalException {
        return null;
    }

    @Override
    public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
        return null;
    }

    @Override
    public int getAttachmentsFileEntriesCount() throws PortalException {
        return 0;
    }

    @Override
    public long getAttachmentsFolderId() {
        return 0;
    }

    @Override
    public long getBackgroundTaskId() {
        return 0;
    }

    @Override
    public long getCompanyId() {
        return 0;
    }

    @Override
    public Date getCompletionDate() {
        return null;
    }

    @Override
    public Date getCreateDate() {
        return null;
    }

    @Override
    public long getGroupId() {
        return 0;
    }

    @Override
    public BaseModel<?> getModel() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getServletContextNames() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public String getStatusLabel() {
        return null;
    }

    @Override
    public String getStatusMessage() {
        return null;
    }

    @Override
    public Map<String, Serializable> getTaskContextMap() {
        return null;
    }

    @Override
    public String getTaskExecutorClassName() {
        return null;
    }

    @Override
    public long getUserId() {
        return 0;
    }

    @Override
    public boolean isCompleted() {
        return false;
    }

    @Override
    public boolean isInProgress() {
        return false;
    }

    @Override
    public void setTaskContextMap(Map<String, Serializable> taskContextMap) {

    }

}