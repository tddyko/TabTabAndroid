package com.yjrlab.tabdoctor.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 14..
 */

public class GroupedSelfDiagnosisModel extends SparseArray<List<SelfDiagnosisModel>> {


    public static GroupedSelfDiagnosisModel groupBySelfDiagnosisModels(List<SelfDiagnosisModel> selfDiagnosisModels) {
        GroupedSelfDiagnosisModel results = new GroupedSelfDiagnosisModel();

        for (SelfDiagnosisModel selfDiagnosisModel : selfDiagnosisModels) {

            int pobId = selfDiagnosisModel.getPobId();
            List<SelfDiagnosisModel> data = results.get(pobId);
            if (data == null) {
                data = new ArrayList<>();
                results.put(pobId, data);
            }

            data.add(selfDiagnosisModel);
        }

        return results;

    }


}
