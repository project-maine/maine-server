package net.dengzixu.maine.mapper;

import net.dengzixu.maine.mapper.provider.GroupNumberMapperSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GroupNumberMapper {
    @InsertProvider(type = GroupNumberMapperSQLProvider.class, method = "addSQLBuilder")
    void add(Long groupID, Long userID);

    @SelectProvider(type = GroupNumberMapperSQLProvider.class, method = "isUserInGroup")
    Integer isUserInGroup(Long groupID, Long userID);
}
