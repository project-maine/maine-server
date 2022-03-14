package net.dengzixu.maine.mapper;

import net.dengzixu.maine.Group;
import net.dengzixu.maine.mapper.provider.GroupMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GroupMapper {
    @InsertProvider(type = GroupMapperProvider.class, method = "addSQLBuilder")
    void create(Long id,String name, String description, Long userID);

    @SelectProvider(type = GroupMapperProvider.class, method = "getByIDSQLBuilder")
    Group getByID(Long groupID);
}
