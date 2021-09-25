package com.yjrlab.tabdoctor.model;

import android.util.SparseArray;

import com.yjrlab.tabdoctor.libs.Dlog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongrakmoon on 2017. 6. 14..
 */

public class GroupedDoubtModel extends SparseArray<List<DoubtModel>> {

    public static GroupedDoubtModel groupByDoubtModel(UserDoubtModel groupDoubtModels) {
        GroupedDoubtModel results = new GroupedDoubtModel();
        List<DoubtModel> doubtModels = groupDoubtModels.getDoubts();
        for (DoubtModel doubtModel : doubtModels) {

            int pobId = doubtModel.getPobId();
            List<DoubtModel> data = results.get(pobId);

            if (data == null) {
                data = new ArrayList<>();
            }
            data.add(doubtModel);
            results.put(pobId, data);
        }
        return results;
    }


}
