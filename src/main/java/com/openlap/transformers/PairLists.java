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

public class PairLists implements DataTransformer {
    TransformedData<List<Pair<String, List>>> transformedData = new TransformedData();

    public PairLists() {
    }

    public TransformedData<?> transformData(OpenLAPDataSet openLAPDataSet) throws UnTransformableData {
        List<OpenLAPDataColumn> columns = openLAPDataSet.getColumnsAsList(true);
        List<String> labels = null;
        List<List<String>> frequencies = null;
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

        for(int i = 0; i < frequencies.size(); ++i) {
            ((List)frequencies.get(i)).add(0, "data" + i);
        }

        ((List)this.transformedData.getData()).add(new Pair("counts", frequencies));
        ((List)this.transformedData.getData()).add(new Pair("names", labels));
        return this.transformedData;
    }

    public TransformedData<List<Pair<String, List>>> getTransformedData() {
        return this.transformedData;
    }
}
