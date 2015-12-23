package wf.my.samlib.service.impl.tools;

public class ItemChanging<T> {
    private String paramName;
    private T oldValue;
    private T newValue;

    public ItemChanging() {
    }

    public ItemChanging(String paramName, T oldValue, T newValue) {
        this.paramName = paramName;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public T getOldValue() {
        return oldValue;
    }

    public void setOldValue(T oldValue) {
        this.oldValue = oldValue;
    }

    public T getNewValue() {
        return newValue;
    }

    public void setNewValue(T newValue) {
        this.newValue = newValue;
    }
}
