package au.com.suncorp.demo.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.form.OptionItem;
import com.adobe.cq.wcm.core.components.models.form.Options;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.drew.lang.annotations.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestDispatcherOptions;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Getter
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, adapters = Options.class, resourceType = "suncorp-demo/components/formOptions", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SuncorpOptions implements Options {
    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate //delegate all method calls to the private object Options
    private Options delegate;
    @JsonIgnore
    private static final String PN_TYPE = "type";
    @JsonIgnore
    private static final String OPTION_ITEMS_PATH = "items";
    @JsonIgnore
    private static final String ID_PREFIX = "form-options";
    @JsonIgnore
    @ChildResource(injectionStrategy = InjectionStrategy.OPTIONAL, name = OPTION_ITEMS_PATH)
    @Nullable
    private List<Resource> itemResources;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private List<OptionItem> optionItems;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private SlingHttpServletRequest request;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private SlingHttpServletResponse response;


    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private Resource resource;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private ResourceResolver resolver;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private String sourceString;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private String listPath;

    @JsonIgnore
    @Self //resolve to current resource
    @Via(type = ResourceSuperType.class) //type of resource we are to resolve to which is our supertype
    @Delegate
    private String datasourceRT;

    public List<SuncorpOptionItemImpl> getRawItems() {
        List<OptionItem> items = new ArrayList<>();
        for (OptionItem item : delegate.getItems()) {
            items.add((OptionItem) item);
        }
        List<SuncorpOptionItemImpl> suncorpItems = new ArrayList<>();
        for (OptionItem item : items) {
            suncorpItems.add((SuncorpOptionItemImpl) item);
        }
        return suncorpItems;
    }

    @ValueMapValue
    public String variant;

//    @JsonIgnore
//    public String getJson() {
//        String jsonString = "no value";
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode node = mapper.valueToTree(this);
//        try {
//            return mapper.writeValueAsString(node);
//        } catch (
//                JsonProcessingException e) {
//            log.error("Cannot output json", e);
//        }
//        return jsonString;
//
//    }

    @Override
    public List<OptionItem> getItems() {
        if (optionItems == null) {
            populateOptionItems();
        }
        return Collections.unmodifiableList(optionItems);
    }


    @JsonIgnore
    private void populateOptionItems() {
        this.optionItems = new ArrayList<>();
        Source source = Source.getSource(sourceString);
        if (source == null) {
            populateOptionItemsFromLocal();
        } else {
            switch (source) {
                case DATASOURCE:
                    populateOptionItemsFromDatasource();
                    break;
                case LIST:
                    populateOptionItemsFromList();
                    break;
                default:
                    populateOptionItemsFromLocal();
            }
        }
    }

    @JsonIgnore
    private void populateOptionItemsFromLocal() {
        if (itemResources != null) {
            for (Resource itemResource : itemResources) {
                OptionItem optionItem = new SuncorpOptionItemImpl(request, resource, itemResource);
                if ((optionItem.isDisabled() || StringUtils.isNotBlank(optionItem.getValue()))) {
                    optionItems.add(optionItem);
                }
            }
        }
    }

    @JsonIgnore
    private void populateOptionItemsFromList() {
        if (StringUtils.isBlank(listPath)) {
            return;
        }
        Resource parent = resolver.getResource(listPath);
        if (parent != null) {
            for (Resource itemResource : parent.getChildren()) {
                OptionItem optionItem = new SuncorpOptionItemImpl(request, resource, itemResource);
                if ((optionItem.isDisabled() || StringUtils.isNotBlank(optionItem.getValue()))) {
                    optionItems.add(optionItem);
                }
            }
        }
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    private void populateOptionItemsFromDatasource() {
        if (StringUtils.isBlank(datasourceRT)) {
            return;
        }
        // build the options by running the datasource code (the list is set as a request attribute)
        RequestDispatcherOptions opts = new RequestDispatcherOptions();
        opts.setForceResourceType(datasourceRT);
        RequestDispatcher dispatcher = request.getRequestDispatcher(resource, opts);
        try {
            if (dispatcher != null) {
                dispatcher.include(request, response);
            } else {
                log.error("Failed to include the datasource at " + datasourceRT);
            }
        } catch (IOException | ServletException | RuntimeException e) {
            log.error("Failed to include the datasource at " + datasourceRT, e);
        }

        // retrieve the datasource from the request and adapt it to form options
        SimpleDataSource dataSource = (SimpleDataSource) request.getAttribute(DataSource.class.getName());
        if (dataSource != null) {
            Iterator<Resource> itemIterator = dataSource.iterator();
            if (itemIterator != null) {
                while (itemIterator.hasNext()) {
                    Resource itemResource = itemIterator.next();
                    OptionItem optionItem = new SuncorpOptionItemImpl(request, resource, itemResource);
                    if ((optionItem.isDisabled() || StringUtils.isNotBlank(optionItem.getValue()))) {
                        optionItems.add(optionItem);
                    }
                }
            }
        }
    }


    private enum Source {
        LOCAL("local"),
        LIST("list"),
        DATASOURCE("datasource");

        private String element;

        Source(String element) {
            this.element = element;
        }

        private static Source getSource(String value) {
            for (Source source : values()) {
                if (StringUtils.equalsIgnoreCase(source.element, value)) {
                    return source;
                }
            }
            return null;
        }
    }
}
