import { useDispatch, useSelector } from 'react-redux';
import './App.css'
import Home from './Pages/Home'
import { Alert, Snackbar } from '@mui/material';
import { closeSnackbar } from './redux/slices/appSlice';

export function App() {

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') return;

    dispatch(closeSnackbar());
  }

  const snackbarOpen = useSelector((state) => state.app.snackbarOpen)
  const snackbarMessage = useSelector((state) => state.app.snackbarMessage)
  const snackbarSeverity = useSelector((state) => state.app.snackbarSeverity)
  const dispatch = useDispatch()

  return (
    <>
      <Home />
      <div >
        <Snackbar
          open={snackbarOpen}
          autoHideDuration={1500}
          onClose={handleClose}
        >
          <Alert variant="filled" onClose={handleClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
            {snackbarMessage}
          </Alert>
        </Snackbar>
      </div>
    </>
  )
}


export default App
