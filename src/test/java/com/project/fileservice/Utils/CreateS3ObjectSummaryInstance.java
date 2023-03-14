package com.project.fileservice.Utils;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class CreateS3ObjectSummaryInstance {
    public static S3ObjectSummary createS3ObjectSumarry(
            Owner owner,
            String storageClass,
            Date lastModified,
            String key,
            String eTag,
            String bucketName,
            long size
            ){
        S3ObjectSummary s3ObjectSummary = new S3ObjectSummary();
        s3ObjectSummary.setOwner(owner);
        s3ObjectSummary.setStorageClass(storageClass);
        s3ObjectSummary.setLastModified(lastModified);
        s3ObjectSummary.setKey(key);
        s3ObjectSummary.setETag(eTag);
        s3ObjectSummary.setBucketName(bucketName);
        s3ObjectSummary.setSize(size);
        return s3ObjectSummary;
    }
    public static ObjectListing createObjectListting(List<S3ObjectSummary> s3ObjectSummaries) throws NoSuchFieldException, IllegalAccessException {
        ObjectListing objectListing = new ObjectListing();
        Field declaredField = ObjectListing.class.getDeclaredField("objectSummaries");
        declaredField.setAccessible(true);
        declaredField.set(objectListing,s3ObjectSummaries);
        return objectListing;
    }
}
