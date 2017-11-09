package ru.it.sd.web.controller.rest;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.it.sd.model.DynamicAuthentication;
import ru.it.sd.model.Person;
import ru.it.sd.model.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

/**
 *
 * @author quadrix
 */
public class FileRestControllerTest extends AbstractWebTest{

    @Autowired
    private FileRestController fileRestController;

    private MockMvc mockMvc;

    @BeforeClass
    public void setup() {
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(fileRestController).build();

        Person person = new Person();
        person.setFirstName("Иван");
        person.setLastName("Иванов");

        User user = new User();
        user.setPerson(person);

        DynamicAuthentication auth = mock(DynamicAuthentication.class);
        when(auth.getUser()).thenReturn(user);
        when(auth.isAuthenticated()).thenReturn(true);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

	@Test
    public void testCreateFile() throws Exception {
	    String data = "poiqwdjfht";
        InputStream is = new ByteArrayInputStream(data.getBytes());
        MockMultipartFile multipartFile = new MockMultipartFile("uploaded-file", "filename.txt", "text/plain", is);

        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "265001916915724");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =
        mockMvc.perform(fileUpload("/rest/service/file/upload")
                .file(multipartFile)
                .contentType(mediaType)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        logger.debug("Content: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testCreateFiles() throws Exception {
        String data = "poiqwdjfhdasdt";

        InputStream is = new ByteArrayInputStream(data.getBytes());
        InputStream is2 = new ByteArrayInputStream(data.getBytes());
        InputStream is3 = new ByteArrayInputStream(data.getBytes());
        InputStream is4 = new ByteArrayInputStream(data.getBytes());

        MockMultipartFile multipartFile = new MockMultipartFile("uploaded-file", "filename.txt", "text/plain", is);
        MockMultipartFile multipartFile2 = new MockMultipartFile("uploaded-file2", "filename2.txt", "text/plain", is2);
        MockMultipartFile multipartFile3 = new MockMultipartFile("uploaded-file3", "filename3.txt", "text/plain", is3);
        MockMultipartFile multipartFile4 = new MockMultipartFile("uploaded-file4", "filename4.txt", "text/plain", is4);

        HashMap<String, String> contentTypeParams = new HashMap<>();
        contentTypeParams.put("boundary", "265001916915724");

        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);

        MvcResult result =
                mockMvc.perform(fileUpload("/rest/service/file/uploadList")
                        .file(multipartFile)
                        .file(multipartFile2)
                        .file(multipartFile3)
                        .file(multipartFile4)
                        .contentType(mediaType)
                )
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(multipartFile.getOriginalFilename())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(multipartFile2.getOriginalFilename())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is(multipartFile3.getOriginalFilename())))
                        .andExpect(MockMvcResultMatchers.jsonPath("$[3].name", Matchers.is(multipartFile4.getOriginalFilename())))
                        .andReturn();
    }


}