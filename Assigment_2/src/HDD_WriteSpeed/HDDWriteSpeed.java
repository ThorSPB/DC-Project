package HDD_WriteSpeed;

import java.io.IOException;

import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

	@Override
	public void initialize(Object... params) {
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException(
				"Method not implemented. Use run(Object) instead");
	}

	@Override
	public void run(Object... options) {
		FileWriter writer = new FileWriter();

		// First argument: either "fs" or "fb"
		String option = (String) options[0];
		// Second argument: whether to delete the files after creation
		Boolean clean = (Boolean) options[1];

		// File path components
		String prefix = "C:\\000-bench\\write-";
		String suffix = ".dat";

		// Index range (write files from minIndex to maxIndex, inclusive)
		int minIndex = 0;
		int maxIndex = 8;

		// File size in bytes (e.g., 256MB, 512MB, 1GB)
		Long fileSize = 512L * 1024 * 1024;  // Example: 512 MB

		// Buffer size in bytes (e.g., 4 KB)
		int bufferSize = 4 * 1024;  // 4 KB

		try {
			if (option.equals("fs")) {
				// Fixed file size
				writer.streamWriteFixedFileSize(prefix, suffix, minIndex,
						maxIndex, fileSize, clean);
			} else if (option.equals("fb")) {
				// Fixed buffer size
				writer.streamWriteFixedBufferSize(prefix, suffix, minIndex,
						maxIndex, bufferSize, clean);
			} else {
				// Invalid option
				throw new IllegalArgumentException("Argument "
						+ options[0].toString() + " is undefined");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void clean() {
		// clean temp files here?
	}

	@Override
	public void cancel() {

	}

}
