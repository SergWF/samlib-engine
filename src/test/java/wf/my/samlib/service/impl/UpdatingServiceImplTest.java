package wf.my.samlib.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import wf.my.samlib.TestHelper;
import wf.my.samlib.entity.Author;
import wf.my.samlib.listener.AuthorRenewListener;
import wf.my.samlib.service.components.AuthorChecker;
import wf.my.samlib.storage.AuthorStorage;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

public class UpdatingServiceImplTest {

    @Spy
    private UpdatingServiceImpl updatingService;
    @Mock
    private AuthorStorage storage;
    @Mock
    private AuthorChecker checker;
    @Mock
    private AuthorRenewListener listener;

    private Author a1 = TestHelper.getAuthor("a1u", "a1n");
    private Author a2 = TestHelper.getAuthor("a2u", "a2n");
    private Author a3 = TestHelper.getAuthor("a3u", "a3n");
    private Author a4 = TestHelper.getAuthor("a4u", "a4n");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        updatingService.setAuthorStorage(storage);
        updatingService.setAuthorChecker(checker);
        Mockito.doReturn(Arrays.asList(a1,a2,a3,a4)).when(storage).getAllAuthors();
        Mockito.doReturn(a1).when(checker).updateAuthor(Mockito.eq(a1), Mockito.any(Date.class));
        Mockito.doReturn(a2).when(checker).updateAuthor(Mockito.eq(a2), Mockito.any(Date.class));
        Mockito.doReturn(a3).when(checker).updateAuthor(Mockito.eq(a3), Mockito.any(Date.class));
        Mockito.doReturn(a4).when(checker).updateAuthor(Mockito.eq(a4), Mockito.any(Date.class));
        Mockito.doReturn(true).when(updatingService).isAuthorUpdated(Mockito.eq(a1), Mockito.any(Date.class));
        Mockito.doReturn(false).when(updatingService).isAuthorUpdated(Mockito.eq(a2), Mockito.any(Date.class));
        Mockito.doReturn(true).when(updatingService).isAuthorUpdated(Mockito.eq(a3), Mockito.any(Date.class));
        Mockito.doReturn(false).when(updatingService).isAuthorUpdated(Mockito.eq(a4), Mockito.any(Date.class));
    }

    @Test
    public void shouldSaveAuthorsAfterChecking() throws Exception {
        updatingService.updateAuthors();
        Mockito.verify(storage).saveAuthor(a1);
        Mockito.verify(storage).saveAuthor(a2);
        Mockito.verify(storage).saveAuthor(a3);
        Mockito.verify(storage).saveAuthor(a4);
    }

    @Test
    public void shouldRaiseAuthorUpdatedEventForUpdatedAuthors() throws Exception {
        updatingService.registerAuthorRenewListener(listener);
        updatingService.updateAuthors();
        Mockito.verify(listener).onRenew(Mockito.eq(a1), Mockito.any(Date.class), Mockito.anyCollection());
        Mockito.verify(listener).onRenew(Mockito.eq(a3), Mockito.any(Date.class), Mockito.anyCollection());
    }
}