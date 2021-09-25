package com.yjrlab.tabdoctor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 8..
 */

public class GroupedHealthDicModel extends BaseModel {
    @SerializedName("mo_code")
    private int departmentCode;
    @SerializedName("mo_name")
    private String departmentName;

    private List<HealthDicModel> healthDics;

    public static List<GroupedHealthDicModel> groupByHealthDic(List<HealthDicModel> healthDicModels) {
        List<GroupedHealthDicModel> results = new ArrayList<>();

        first:
        for (HealthDicModel healthDicModel : healthDicModels) {

            for (GroupedHealthDicModel result : results) {

                if (result.departmentCode == healthDicModel.getDepartmentCode()) {
                    if (result.healthDics == null) {
                        result.healthDics = new ArrayList<>();
                    }

                    result.healthDics.add(healthDicModel);
                    continue first;
                }
            }

            GroupedHealthDicModel data = new GroupedHealthDicModel();
            data.departmentCode = healthDicModel.getDepartmentCode();
            data.departmentName = healthDicModel.getDepartmentName();
            data.healthDics = new ArrayList<>();
            data.healthDics.add(healthDicModel);
            results.add(data);
        }
        return results;
    }

    public int getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(int departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<HealthDicModel> getHealthDics() {
        return healthDics;
    }

    public void setHealthDics(List<HealthDicModel> healthDics) {
        this.healthDics = healthDics;
    }
}
