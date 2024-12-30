
public class DateUtil {
    public void dateUtil() {
        //System.out.println("Hello, World!");
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.toString());

        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();

        // 格式化为分钟
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String timestampMinute = now.format(formatter);

        System.out.println("Timestamp to minute: " + timestampMinute);
    }
}