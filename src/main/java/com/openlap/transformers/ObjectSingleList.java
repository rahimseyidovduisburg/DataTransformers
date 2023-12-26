package com.openlap.transformers;

import com.openlap.dataset.OpenLAPDataColumn;
import com.openlap.dataset.OpenLAPDataSet;
import com.openlap.exceptions.UnTransformableData;
import com.openlap.template.DataTransformer;
import com.openlap.template.model.TransformedData;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectSingleList implements DataTransformer {
    TransformedData<LinkedHashMap<String, Object[]>> transformedData = new TransformedData();

    public ObjectSingleList() {
    }

    public TransformedData<?> transformData(OpenLAPDataSet openLAPDataSet) throws UnTransformableData {
        List<String> xData = ((OpenLAPDataColumn)openLAPDataSet.getColumns().get("xAxisStrings")).getData();
        List<String> yData = ((OpenLAPDataColumn)openLAPDataSet.getColumns().get("yAxisValues")).getData();
        List<String> clusters = ((OpenLAPDataColumn)openLAPDataSet.getColumns().get("GroupBy")).getData();
        this.transformedData.setData(new LinkedHashMap());
        if (xData != null) {
            for(int i = 0; i < xData.size(); ++i) {
                if (!((LinkedHashMap)this.transformedData.getData()).containsKey(xData.get(i))) {
                    ((LinkedHashMap)this.transformedData.getData()).put((String)clusters.get(i) + "_x", Arrays.asList((List)Arrays.stream(String.valueOf(xData.get(i)).split(",")).mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList())).toArray());
                    ((LinkedHashMap)this.transformedData.getData()).put(String.valueOf(clusters.get(i)), Arrays.asList((List)Arrays.stream(String.valueOf(yData.get(i)).split(",")).mapToDouble(Double::parseDouble).boxed().collect(Collectors.toList())).toArray());
                }
            }
        }

        return this.transformedData;
    }

    public TransformedData<LinkedHashMap<String, Object[]>> getTransformedData() {
        return this.transformedData;
    }
}