package pdservice.dto;

import pdservice.entity.AttributeValues;

import java.util.List;
import java.util.Map;

public class AttributeValuesResponse {

    List<AttributeValues> savedAttributes;

    //Key : Reason to fail
    Map<String,List<AttributeValues>> failedAttributes;

    public List<AttributeValues> getSavedAttributes() {
        return savedAttributes;
    }

    public void setSavedAttributes(List<AttributeValues> savedAttributes) {
        this.savedAttributes = savedAttributes;
    }

    public Map<String, List<AttributeValues>> getFailedAttributes() {
        return failedAttributes;
    }

    public void setFailedAttributes(Map<String, List<AttributeValues>> failedAttributes) {
        this.failedAttributes = failedAttributes;
    }
}
