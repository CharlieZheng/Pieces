import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by zhy on 15/5/3.
 * 使用方法
 * 例如：基准 1280 * 800 ，额外支持尺寸：1152 * 735；4500 * 3200；
 * java GenerateValueFiles 800 1280 735,1152_3200,4500
 */
public class GenerateValueFiles {

    private int baseW;
    private int baseH;

    private String dirStr = "./res";

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    /**
     * {0}-HEIGHT
     */
    private final static String VALUE_TEMPLATE = "values-{0}x{1}";

    // private static final String SUPPORT_DIMESION = "320,480;480,800;480,854;540,960;600,1024;720,1184;720,1196;720,1280;768,1024;800,1280;1080,1812;1080,1920;1440,2560;";
	
	// 最多是1080*1920的尺寸
	private static final String SUPPORT_DIMESION = "" // 参考自：https://material.io/devices/
												 //+ "272,340;"
												// + "280,280;"
												
												// + "312,390;"
												// + "320,290;"
												 //+ "320,320;"
												// + "320,325;"
												// + "320,330;"
												 + "320,480;" // iPhone
												 + "480,800;" // Additional
												 + "480,854;" // Android One
												 + "540,960;" // Additional
												 + "600,1024;" // Additional
												 + "640,960;" // iPhone 4
												 + "640,1136;" // iPhone 5
												 + "720,1184;" // Additional
												 + "720,1196;" // Additional
												 + "720,1280;" // Moto G
												 
												 + "750,1334;" // iPhone 6
												 + "768,1024;" // iPad
												 + "768,1280;" // Nexus 4
												 + "800,1280;" // Nexus 7 ('12)、Samsung Galaxy Tab
												 + "1080,1800;" // 魅族MX3
												 + "1080,1920;" // Google Pixel、HTC One M8、HTC One M9、iPhone 6 Plus、LG G2
												 
												 + "1200,1920;" // Nexus7 ('13)
												// + "1366,768;"
												// + "1440,900;"
												 + "1440,2560;" // Google Pixel XL
												 + "1536,2048;" // iPad Mini Retina
												 + "1600,2560;" // Dell Venue 8
												// + "1920,1080;" 
												// + "1920,1200;" // Sony Xperia Z3 Tablet
												// + "2048,1536;" // Nexus 9
												// + "2160,1440;"
												// + "2304,1440;"
												// + "2560,1440;"
												// + "2560,1600;"
												// + "2560,1700;"
												// + "2732,2048;" // iPad Pro
												// + "2736,1824;"
												// + "2880,1800;"
												// + "3000,2000;"
												// + "5120,2880;"
												;

    private String supportStr = SUPPORT_DIMESION;

    public GenerateValueFiles(int baseX, int baseY, String supportStr) {
        this.baseW = baseX;
        this.baseH = baseY;

        if (!this.supportStr.contains(baseX + "," + baseY)) {
            this.supportStr += baseX + "," + baseY + ";";
        }

        this.supportStr += validateInput(supportStr);

        System.out.println(supportStr);

        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdir();

        }
        System.out.println(dir.getAbsoluteFile());

    }

    /**
     * @param supportStr
     *            w,h_...w,h;
     * @return
     */
    private String validateInput(String supportStr) {
        StringBuffer sb = new StringBuffer();
        String[] vals = supportStr.split("_");
        int w = -1;
        int h = -1;
        String[] wh;
        for (String val : vals) {
            try {
                if (val == null || val.trim().length() == 0)
                    continue;

                wh = val.split(",");
                w = Integer.parseInt(wh[0]);
                h = Integer.parseInt(wh[1]);
            } catch (Exception e) {
                System.out.println("skip invalidate params : w,h = " + val);
                continue;
            }
            sb.append(w + "," + h + ";");
        }

        return sb.toString();
    }

    public void generate() {
        String[] vals = supportStr.split(";");
        for (String val : vals) {
            String[] wh = val.split(",");
            generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
        }

    }

    private void generateXmlFile(int w, int h) {

        StringBuffer sbForWidth = new StringBuffer();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForWidth.append("<resources>");
        float cellw = w * 1.0f / baseW;

        System.out.println("width : " + w + "," + baseW + "," + cellw);
        for (int i = 1; i < baseW; i++) {
            sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellw * i) + ""));
        }
        sbForWidth.append(WTemplate.replace("{0}", baseW + "").replace("{1}",
                w + ""));
        sbForWidth.append("</resources>");

        StringBuffer sbForHeight = new StringBuffer();
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForHeight.append("<resources>");
        float cellh = h *1.0f/ baseH;
        System.out.println("height : "+ h + "," + baseH + "," + cellh);
        for (int i = 1; i < baseH; i++) {
            sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellh * i) + ""));
        }
        sbForHeight.append(HTemplate.replace("{0}", baseH + "").replace("{1}",
                h + ""));
        sbForHeight.append("</resources>");

        File fileDir = new File(dirStr + File.separator
                + VALUE_TEMPLATE.replace("{0}", h + "")//
                        .replace("{1}", w + ""));
        fileDir.mkdir();

        File layxFile = new File(fileDir.getAbsolutePath(), "lay_x.xml");
        File layyFile = new File(fileDir.getAbsolutePath(), "lay_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sbForWidth.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sbForHeight.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

    public static void main(String[] args) {
        int baseW = 320;
        int baseH = 400;
        String addition = "";
        try {
            if (args.length >= 3) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
                addition = args[2];
            } else if (args.length >= 2) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
            } else if (args.length >= 1) {
                addition = args[0];
            }
        } catch (NumberFormatException e) {

            System.err
                    .println("right input params : java -jar xxx.jar width height w,h_w,h_..._w,h;");
            e.printStackTrace();
            System.exit(-1);
        }

        new GenerateValueFiles(baseW, baseH, addition).generate();
    }

}