package net.babybaby.agijagi.notice;


public class NoticeModel {
    private String _title;
    private String _description;
    private String _reg_date;

    public NoticeModel() {

    }

    public void SetTitle(String value)
    {
        _title = value;
    }

    public String getTitle()
    {
        return _title;
    }

    public void SetDescription(String value)
    {
        _description = value;
    }

    public String getDescription()
    {
        return _description;
    }

    public void SetRegDate(String value)
    {
        _reg_date = value;
    }

    public String getRegDate()
    {
        return _reg_date;
    }
}
