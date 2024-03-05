package au.com.suncorp.demo.core.models;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class moobject {
    @Getter
    @Inject
    @Named("jcr:content/title")
    private String title;

    @Getter
    @Inject
    @Named("jcr:content/desc")
    private String desc;

    @Getter
    @Inject
    @Named("jcr:content/type")
    private String type;

    @Getter
    @Inject
    @Named("jcr:content/offerTitle")
    private String offerTitle;

    @Getter
    @Inject
    @Named("jcr:content/offerDesc")
    private String offerDesc;

    @Getter
    @Inject
    @Named("jcr:content/icon")
    private String icon;


    private String getRandom() {
        return Integer.toString((int) (Math.random() * 1000));
    }
}
