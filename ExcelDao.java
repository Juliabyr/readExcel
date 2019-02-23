package com.unionpay.tech.db;

import com.unionpay.tech.db.entity.TbExcel;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * Created by zhuye on 2019/2/18.
 */
public class ExcelDao {

    private QueryRunner runner = null;//查询运行器
    public ExcelDao() {
        runner = new QueryRunner();
    }
    public void add(TbExcel p) throws SQLException {
        String sql = "insert into tb_excel(id, name, movie) values(?,?,?)";
        runner.update(DBUtils.getConnection(), sql, p.getId(), p.getName(), p.getExcel());
    }
    public byte[] select(int id) throws SQLException {
        String sql = "select excel from tb_excel where id=?";
        byte[] data = runner.query(DBUtils.getConnection(), sql, new ScalarHandler<>(), id);
        return data;
    }
}
