package com.openlap.transformers;

import com.openlap.dataset.OpenLAPDataColumn;
import com.openlap.dataset.OpenLAPDataSet;
import com.openlap.exceptions.UnTransformableData;
import com.openlap.template.DataTransformer;
import com.openlap.template.model.TransformedData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class ObjectList implements DataTransformer {
    TransformedData<LinkedHashMap<String, Object[]>> transformedData = new TransformedData();

    public ObjectList() {
    }

    public TransformedData<?> transformData(OpenLAPDataSet openLAPDataSet) throws UnTransformableData {
        List<String> labels = ((OpenLAPDataColumn)openLAPDataSet.getColumns().get("xAxisStrings")).getData();
        List<String> groupBy = ((OpenLAPDataColumn)openLAPDataSet.getColumns().get("GroupBy")).getData();
        List<Double> frequencies = ((OpenLAPDataColumn)openLAPDataSet.getColumns().get("yAxisValues")).getData();
        List<String> uniqueGroupItems = new ArrayList();
        Iterator var6 = groupBy.iterator();

        while(var6.hasNext()) {
            String item = (String)var6.next();
            if (!uniqueGroupItems.contains(item)) {
                uniqueGroupItems.add(item);
            }
        }

        int dataArraySize = uniqueGroupItems.size();
        this.transformedData.setData(new LinkedHashMap());
        ((LinkedHashMap)this.transformedData.getData()).put("Header", new Object[dataArraySize]);

        int i;
        for(i = 0; i < uniqueGroupItems.size(); ++i) {
            ((Object[])((LinkedHashMap)this.transformedData.getData()).get("Header"))[i] = uniqueGroupItems.get(i);
        }

        if (labels != null) {
            for(i = 0; i < labels.size(); ++i) {
                if (!((LinkedHashMap)this.transformedData.getData()).containsKey(labels.get(i))) {
                    Object[] emptyArray = new Object[dataArraySize];
                    Arrays.fill(emptyArray, 0);
                    ((LinkedHashMap)this.transformedData.getData()).put((String)labels.get(i), emptyArray);
                }

                ((Object[])((LinkedHashMap)this.transformedData.getData()).get(labels.get(i)))[uniqueGroupItems.indexOf(groupBy.get(i))] = frequencies.get(i);
            }
        }

        return this.transformedData;
    }

    public TransformedData<LinkedHashMap<String, Object[]>> getTransformedData() {
        return this.transformedData;
    }
}

