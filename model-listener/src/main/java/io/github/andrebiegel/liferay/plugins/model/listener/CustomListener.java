package io.github.andrebiegel.liferay.plugins.model.listener;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = ModelListener.class)
public class CustomListener extends BaseModelListener<AssetCategory> {

    private static final Log log = LogFactoryUtil.getLog(CustomListener.class);

    @Override
    public void onAfterUpdate(AssetCategory model) throws ModelListenerException {
        log.debug("here i am ");
        /**
         * Access AssetCategory do things needed
         * 
         * 
         */
    }

}