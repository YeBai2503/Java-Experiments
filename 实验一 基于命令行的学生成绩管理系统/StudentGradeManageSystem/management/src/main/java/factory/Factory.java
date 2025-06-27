package factory;

import entity.AbstractNameAndId;

public interface Factory {
    public AbstractNameAndId produce(int id,String name,int one,int two,int three);
}
