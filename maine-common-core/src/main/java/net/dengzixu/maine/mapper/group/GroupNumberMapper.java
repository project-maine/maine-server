package net.dengzixu.maine.mapper.group;

import net.dengzixu.maine.entity.dataobject.GroupNumberDO;
import net.dengzixu.maine.mapper.provider.group.GroupNumberMapperSQLProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupNumberMapper {
    @InsertProvider(type = GroupNumberMapperSQLProvider.class, method = "addSQLBuilder")
    void add(Long groupID, Long userID);

    @UpdateProvider(type = GroupNumberMapperSQLProvider.class, method = "modifyStatusSQLBuilder")
    void modifyStatus(Long groupID, Long userID, Integer Status);

    @SelectProvider(type = GroupNumberMapperSQLProvider.class, method = "isUserInGroup")
    Integer isUserInGroup(Long groupID, Long userID, Boolean allowLeave);

    @SelectProvider(type = GroupNumberMapperSQLProvider.class, method = "getGroupNumberListSQLBuilder")
    List<GroupNumberDO> getGroupNumberList(Long groupID, Long userID);
}
