package inno_x_clients.constClass;

public class Const {

    public static final int HTTP_CODE_OK = 200;
    //������ ������� ��������. �������� ���������� ������� ������� �� ������ HTTP:
    //
    //GET: ������ ��� ������� � ������� � ���� ���������.
    //HEAD: ����� �������� ���������, �� ���� ��������� ���.
    //PUT ��� POST: ������, ����������� ��������� ��������, ������� � ���� ���������.
    //TRACE: ���� ��������� �������� ��������� �������, ���������� ��������.
    public static final int HTTP_CODE_CREATE = 201;
    //������ �������� �������, � � ���������� ��� ������ ����� ������. ������ ��� �����,
    // ������������ �� ������� POST ��� PUT.
    public static final int HTTP_CODE_BAD_REQUEST = 400;
    //������ �� ����� ��� �� ����� ������������ ������ ��-�� ����-��, ��� �������������� ���
    // ������ ������� (��������, ������������ ���������, ������ ��� ������������� �������).
    public static final int HTTP_CODE_UNAUTHORIZED = 401;
    //����������������
    public static final int HTTP_CODE_FORBIDDEN = 403;
    //������ �� ����� ���� ������� � ��������, �� ���� �� �������������,
    // ������� ������ ������������ ������������ ����������� ������.
    public static final int HTTP_CODE_NOT_FOUND = 404;
    //������ �� ����� ����� ����������� ������.
    public static final int HTTP_CODE_INTERNAL_SERVER_ERROR = 500;
    public static final int HTTP_CODE_GATEWAY_TIMEOUT = 504;

}