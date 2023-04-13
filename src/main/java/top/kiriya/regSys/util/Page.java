package top.kiriya.regSys.util;

import java.util.List;

/**
 * @author Kiriya
 * @date 2023/2/1 18:01
 */
public class Page<T> {
    private int total;//数据总数
    private List<T> rows;//分页数据
    private int size;//查询条数
    private int current; //页码

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", rows=" + rows +
                ", size=" + size +
                ", current=" + current +
                '}';
    }

}