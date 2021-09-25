package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 8..
 */

public class GroupedBodyPartSymptomModel extends BaseModel {
    @SerializedName("pob_name")
    private String pobName;
    @SerializedName("pob_id")
    private int pobId;
    @SerializedName("disease")
    private List<BodyPartSymptom> bodyPartSymptoms;

    public static List<GroupedBodyPartSymptomModel> groupByBodyPartSymptom(List<BodyPartSymptom> bodyPartSymptoms) {
        List<GroupedBodyPartSymptomModel> results = new ArrayList<>();

        first:
        for (BodyPartSymptom bodyPartSymptom : bodyPartSymptoms) {

            for (GroupedBodyPartSymptomModel result : results) {

                if (result.pobId == bodyPartSymptom.getPobId()) {
                    if (result.bodyPartSymptoms == null) {
                        result.bodyPartSymptoms = new ArrayList<>();
                    }

                    result.bodyPartSymptoms.add(bodyPartSymptom);
                    continue first;
                }
            }

            GroupedBodyPartSymptomModel data = new GroupedBodyPartSymptomModel();
            data.pobId = bodyPartSymptom.getPobId();
            data.pobName = bodyPartSymptom.getPobName();
            data.bodyPartSymptoms = new ArrayList<>();
            data.bodyPartSymptoms.add(bodyPartSymptom);
            results.add(data);

        }

        return results;
    }

    public String getPobName() {
        return pobName;
    }

    public void setPobName(String pobName) {
        this.pobName = pobName;
    }

    public int getPobId() {
        return pobId;
    }

    public void setPobId(int pobId) {
        this.pobId = pobId;
    }

    public List<BodyPartSymptom> getBodyPartSymptoms() {
        return bodyPartSymptoms;
    }

    public void setBodyPartSymptoms(List<BodyPartSymptom> bodyPartSymptoms) {
        this.bodyPartSymptoms = bodyPartSymptoms;
    }
}
