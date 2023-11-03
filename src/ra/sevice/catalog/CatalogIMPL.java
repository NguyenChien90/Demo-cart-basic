package ra.sevice.catalog;

import ra.config.Config;
import ra.model.Catalogs;
import ra.model.Users;

import java.util.ArrayList;
import java.util.List;

public class CatalogIMPL implements ICatalogService{
    static Config<List<Catalogs>> config = new Config<>();
    static List<Catalogs> catalogsList;
    static {
//        catalogsList = config.readFile(Config.URL_CATALOG);
        if (catalogsList == null ){
            catalogsList = new ArrayList<>();
//            catalogsList.add(new Catalogs(1,"C001","111",true));
//            catalogsList.add(new Catalogs(2,"C002","222",true));
//            catalogsList.add(new Catalogs(3,"C003","333",true));
//            config.writeFile(Config.URL_CATALOG,catalogsList);
        }
    }
    @Override
    public List<Catalogs> findAll() {
        return catalogsList;
    }

    @Override
    public void save(Catalogs catalogs) {
        if (findById(catalogs.getCatalogId()) == null){
            catalogsList.add(catalogs);
            updateData();
        }else {
            catalogsList.set(catalogsList.indexOf(catalogs),catalogs);
            updateData();
        }
    }

    @Override
    public void delete(int id) {
        catalogsList.remove(findById(id));
    }

    @Override
    public Catalogs findById(int id) {
        for (Catalogs catalogs : catalogsList){
            if (catalogs.getCatalogId() == id){
                return catalogs;
            }
        }
        return null;
    }

    @Override
    public int getNewId() {
        int idMax = 0;
        for (Catalogs catalogs : catalogsList) {
            if (catalogs.getCatalogId() > idMax){
                idMax = catalogs.getCatalogId();
            }
        }
        return (idMax + 1);
    }

    @Override
    public void updateData() {
        config.writeFile(Config.URL_CATALOG,catalogsList);
    }
}
