import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hanrong on 2017/5/4.
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        Test test = new Test();
//        File file = new File("C:\\Users\\hanrong\\IdeaProjects\\untitled\\src\\layout");
//        if (file.exists()) {
//            File[] files = file.listFiles();
//            if (files.length == 0) {
//                System.out.println("文件夹是空的!");
//                return;
//            } else {
//                int i = 0;
//                for (File file2 : files) {
//                    if (file2.isDirectory()) {
//                        System.out.println("文件夹:" + file2.getAbsolutePath());
//                    } else {
//                        System.out.println("文件:" + file2.getAbsolutePath());
//                        Thread.sleep(500);
//
////                        test.string2File(test.file2String(file2.getAbsolutePath()), file2.getParent() + "\\" + file2.getName() + "----v----" + (i++));
//                    test.update(test.file2String(file2.getAbsolutePath()));
//                        Thread.sleep(500);
//                    }
//                }
//            }
//        } else {
//            System.out.println("文件不存在!");
//        }
        test.update("fef           regarewe \"\"4px\" \"43px \"fewf");
    }

    private void update(String str) {
        //生成Pattern对象并且编译一个简单的正则表达式"Kelvin"
//        Pattern p = Pattern.compile("(?<=\")(\\s)*\\d(\\s)*[px|sp|dp](\\s)*(?=\")");
        Pattern p1 = Pattern.compile("(?<=\")(\\s)*(\\d)+(?=(\\s)*(px|dp|sp)(\\s)*\")");
        Pattern p2 = Pattern.compile("(?<=\")(\\s)*(\\d)+(\\s)*(px|dp|sp)(?=(\\s)*\")");
//用Pattern类的matcher()方法生成一个Matcher对象
        Matcher m1 = p1.matcher(str);
        Matcher m2 = p2.matcher(str);
        while (m1.find()) {
System.out.println(m1.group());
//            int temp = Integer.valueOf(m1.group());
//            System.out.println("        " + temp);
//            if (m2.find()) {
//                str = m2.re("@dimen/x" + temp);
//            }
        }
//        System.out.println(str);
    }

    private String file2String(String fileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));

        String content = "";
        StringBuilder sb = new StringBuilder();

        while (content != null) {
            content = bf.readLine();

            if (content == null) {
                break;
            }

            sb.append(content.trim());
        }

        bf.close();
        return sb.toString();

    }

    public static File string2File(String fileContent, String path) {
        byte[] b = fileContent.getBytes();
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(path);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
}
