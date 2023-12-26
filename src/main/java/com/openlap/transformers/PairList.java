package com.openlap.transformers;

import com.openlap.dataset.OpenLAPColumnDataType;
import com.openlap.dataset.OpenLAPDataColumn;
import com.openlap.dataset.OpenLAPDataSet;
import com.openlap.exceptions.UnTransformableData;
import com.openlap.template.DataTransformer;
import com.openlap.template.model.TransformedData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;

public class PairList implements DataTransformer {
    TransformedData<List<Pair<String, Float>>> transformedData = new TransformedData();

    public PairList() {
    }

    public TransformedData<?> transformData(OpenLAPDataSet openLAPDataSet) throws UnTransformableData {
        List<OpenLAPDataColumn> columns = openLAPDataSet.getColumnsAsList(true);
        List<String> labels = null;
        List<Float> frequencies = null;
        this.transformedData.setData(new ArrayList());
        Iterator var5 = columns.iterator();

        while(var5.hasNext()) {
            OpenLAPDataColumn column = (OpenLAPDataColumn)var5.next();
            if (column.getConfigurationData().getType().equals(OpenLAPColumnDataType.Numeric)) {
                frequencies = column.getData();
            } else {
                labels = column.getData();
            }
        }

        if (labels != null) {
            for(int i = 0; i < labels.size(); ++i) {
                ((List)this.transformedData.getData()).add(new Pair((String)labels.get(i), Float.parseFloat(String.valueOf(frequencies.get(i)))));
            }
        }

        return this.transformedData;
    }

    public TransformedData<List<Pair<String, Float>>> getTransformedData() {
        return this.transformedData;
    }
}
