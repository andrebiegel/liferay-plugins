package io.github.andrebiegel.display.context;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import io.github.andrebiegel.display.navigation.NavigationItemProvider;

@Component(immediate = true, service = NavigationBarDisplayContextFactory.class)
public class NavigationBarDisplayContextFactoryImpl implements NavigationBarDisplayContextFactory {

    private List<NavigationItemProvider> navItemProviders = new CopyOnWriteArrayList<>();

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    public void addNavigationItemProvider(NavigationItemProvider navigationItemProvider) {
        this.navItemProviders.add(navigationItemProvider);
    }

    public void removeNavigationItemProvider(NavigationItemProvider navigationItemProvider) {
        this.navItemProviders.remove(navigationItemProvider);
    }

    @Override
    public NavigationBarDisplayContext provide() {
        return new NavigationBarDisplayContext(this.navItemProviders);
    }

}