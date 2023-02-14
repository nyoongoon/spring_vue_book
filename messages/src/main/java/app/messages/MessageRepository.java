package app.messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MessageRepository {
    private final static Log logger = LogFactory.getLog(MessageRepository.class);
    private DataSource dataSource; // spring-boot-start-jdbc에서 의존성 주입됨
    public MessageRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Message saveMessage(Message message){
        System.out.println("saveMessage");
        // DB연결을 위한 스프링 헬퍼 클래스인 DataSourceUtils 사용
        Connection c = DataSourceUtils.getConnection(dataSource);
        try{
            String insertSql = "INSERT INTO messages (`id`, `text`, `created_date`) VALUES (null, ?, ?)";
            PreparedStatement ps = c.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            //SQL에 필요한 매개변수
            ps.setString(1, message.getText());
            ps.setTimestamp(2, new Timestamp(message.getCreatedDate().getTime()));
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                // 새로 저장된 메시지 id 가져오기
                ResultSet result = ps.getGeneratedKeys();
                if(result.next()){
                    int id = result.getInt(1);
                    return new Message(id, message.getText(), message.getCreatedDate());
                }else{
                    logger.error("Failed to retrieve id, No row in result set");
                    return null;
                }
            }else{
                //insert 실패
                return null;
            }
        }catch (SQLException ex){
            logger.error("Failed to save message", ex);
            //연결한 커넥션 닫기. 닫지 않으면 연결은 커넥션 풀로 반환되지 않고 다른 연결에 의해 재사용된다.
            try{
                c.close();
            }catch (SQLException e){
                logger.error("Failed to close connection", e);
            }
        }finally {
            DataSourceUtils.releaseConnection(c, dataSource);
        }
        return null;
    }
}
