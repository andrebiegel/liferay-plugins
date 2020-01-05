package io.github.andrebiegel.display.navigation;

import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.petra.function.UnsafeConsumer;

public interface NavigationItemProvider {

    public UnsafeConsumer<NavigationItem, Exception> item(final String activeEntry, final HttpServletRequest request,
            final RenderResponse renderResponse);

    public String getPage(String navigation);
}


    