package kr.or.ddit.compiler.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.exception.CommonException;
import kr.or.ddit.lecture.service.ICategoryService;
import kr.or.ddit.vo.CategoryVO;

@RestController
@RequestMapping(value="/compiler", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CompilerController {
	
	@Inject
	ICategoryService categoryService;
	
	@Value("#{appInfo.compileFolder}")
	String saveFolderUrl;
	
	@Inject
	WebApplicationContext container;
	
	Logger logger =  LoggerFactory.getLogger(CompilerController.class);
	
	File saveFolder;
	@PostConstruct // init-method
 	public void init() {
		// 파일 저장 위치 : 웹 리소스의 형태
		String fileSystemPath = container.getServletContext().getRealPath(saveFolderUrl);
		saveFolder = new File(fileSystemPath);
		if(!saveFolder.exists()) saveFolder.mkdirs();
	}
	
	@PostMapping
	public Map<String, Object> compileResult(String editor, String editorType, String userId){
		
		// 입력내용을 받아 파일 생성하기
		File tempFile = new File(saveFolder, userId+"." + editorType);
		
		try(
			OutputStream out = new FileOutputStream(tempFile);
		) {
			FileUtils.write(tempFile, editor);
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// 결과
		Map<String, Object> result = new HashMap<>();
		String executeResult = null;
		try {
			if("java".equals(editorType)) {
				// 파일 경로 지정하고 컴파일 실행
				executeResult = execute("javac " + userId + "." + editorType + " -encoding UTF-8", saveFolder);
				if(StringUtils.isBlank(executeResult)) {
					executeResult = execute("java " + userId, saveFolder);
				}
				// 파일 경로 지정하고 컴파일 실행
			}else if("cpp".equals(editorType)) {
				String executeName = "g++ -o " + userId + " " + userId + ".cpp";
				executeResult = execute(executeName, saveFolder);
				if(StringUtils.isBlank(executeResult)) {
					executeResult = execute(userId + ".exe", saveFolder);
				}
			}else if("c".equals(editorType)) {
				String executeName = "gcc -o " + userId + " " + userId + ".c";
				executeResult = execute(executeName, saveFolder);
				if(StringUtils.isBlank(executeResult)) {
					executeResult = execute(userId + ".exe", saveFolder);
				}
			}else if("py".equals(editorType)) {
				executeResult = execute("python " + userId + ".py", saveFolder);
			}
			result.put("result", true);
			result.put("logText", executeResult);
			
		}catch(CommonException e) {
			result.put("result", false);
			result.put("message", "컴파일중에 에러가 있습니다.");
		}
		
		return result;
	}
	
	// 컴파일러 select 옵션 
	@GetMapping("editorType")
	public Map<String, Object> getEditorType(){
		
		List<CategoryVO> categoryList = categoryService.retriveCategoryList();
		// 결과
		Map<String, Object> result = new HashMap<>();
		
		if(categoryList.size() > 0) {
			result.put("result", true);
			result.put("categoryList", categoryList);
		}else {
			result.put("result", false);
			result.put("message", "옵션이 없습니다.");
		}
		
		return result;
	}
	
	
	/**
     * cmd 명령어 실행
     *
     * @param cmd
     */
    public String execute(String cmd, File dir) {
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        StringBuffer output = new StringBuffer(); // 스트링 버퍼
        BufferedReader bufferReader = null; // 버퍼
        String msg = null; // 메시지
 
        List<String> cmdList = new ArrayList<String>();
 
        // 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
            cmdList.add("cmd");
            cmdList.add("/c");
        } else {
            cmdList.add("/bin/sh");
            cmdList.add("-c");
        }
        // 명령어 셋팅
        cmdList.add(cmd);
        String[] array = cmdList.toArray(new String[cmdList.size()]);
 
        try {
        	
            // 명령어 실행
            process = runtime.exec(array, null, dir);
            
            // shell 실행이 정상 동작했을 경우
            bufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));
 
            while ((msg = bufferReader.readLine()) != null) {
                output.append(msg + System.getProperty("line.separator"));
            }
            bufferReader.close();
            
            // shell 실행시 에러가 발생했을 경우
            bufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "EUC-KR"));
            while ((msg = bufferReader.readLine()) != null) {
                output.append(msg + System.getProperty("line.separator"));
            }
 
            // 프로세스의 수행이 끝날때까지 대기
            process.waitFor();
            System.out.println("exitValue = " + process.exitValue());
 
            // shell 실행이 정상 종료되었을 경우
            if (process.exitValue() == 0) {
                logger.info("shell 실행 성공 : " + output.toString());
            } else {
            	logger.info("shell 실행 실패 : " + output.toString());
//                // shell 실행이 비정상 종료되었을 경우
//                throw new CommonException("shell 실행 비정상 종료 : "+ output.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                process.destroy();
                if (bufferReader != null) bufferReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
        return output.toString().replace("^", "");
    }
	
}
