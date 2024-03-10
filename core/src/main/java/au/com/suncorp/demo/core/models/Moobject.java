package au.com.suncorp.demo.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Slf4j

@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Getter
public class Moobject {
    @ValueMapValue
    private String title;

    @ValueMapValue
    private String desc;

    @ValueMapValue
    private String type;

    @ValueMapValue
    private String offerTitle;

    @ValueMapValue
    private String offerDesc;

    @ValueMapValue
    private String icon;

    @JsonIgnore
    public String getRandom() {
        return Integer.toString((int) (Math.random() * 1000));
    }

    //    public String getJSON() {
//        return "{ \"title\": \"" + title + "\", \"desc\": \"" + desc + "\", \"type\": \"" + type + "\", \"offerTitle\": \"" + offerTitle + "\", \"offerDesc\": \"" + offerDesc + "\", \"icon\": \"" + icon + "\" }";
//    }
//    @JsonIgnore
//    @SlingObject
//    private Resource currentResource;

    @JsonIgnore
    public String getJson() {
        String jsonString = "no value";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(this);
        try {
            return mapper.writeValueAsString(node);
        } catch (
                JsonProcessingException e) {
            log.error("Cannot output json", e);
        }
        return jsonString;

    }
}
