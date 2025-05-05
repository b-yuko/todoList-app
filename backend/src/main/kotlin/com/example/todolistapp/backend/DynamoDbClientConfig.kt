package com.example.todolistapp.backend

import com.example.todolistapp.backend.model.TaskModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

/**
 * DynamoDBクライアントの設定クラス。
 * DynamoDbClient（低レベルAPI）と DynamoDbEnhancedClient（高レベルAPI）をBeanとして提供する。
 */
@Configuration
class DynamoDbClientConfig(
    private val properties: DynamoDbProperties,
) {
    /**
     * DynamoDbClient を生成して Bean として登録する。
     * DynamoDB の低レベル API にアクセスするためのクライアント。
     */
    @Bean
    fun dynamoDbRawClient(): DynamoDbClient {
        // 接続先やリージョンの確認ログ（デバッグ用）
        println("DynamoDB client initialized: url=${properties.dynamoDbUrl}, region=${properties.dynamoDbRegion}")

        // DynamoDbClient のインスタンスを構築して返す
        return DynamoDbClient
            .builder()
            .region(Region.of(properties.dynamoDbRegion))
            // ローカル環境用にDynamoDBのエンドポイントを上書き
            .endpointOverride(URI.create(properties.dynamoDbUrl))
            .build()
    }

    /**
     * DynamoDbEnhancedClient を生成して Bean として登録する。
     * DynamoDB の高レベル API にアクセスできるようにする（データモデルとのマッピングが簡単になる）。
     */
    @Bean
    fun dynamoDbEnhancedClient(): DynamoDbEnhancedClient {
        // 先に定義した raw client を使って enhanced client を構築
        return DynamoDbEnhancedClient
            .builder()
            .dynamoDbClient(dynamoDbRawClient())
            .build()
    }

    /**
     * KeyValueModel 用の DynamoDbTable を生成して Bean として登録する。
     * DynamoDbEnhancedClient を使って、DynamoDB テーブルと Kotlin データクラス（KeyValueModel）を
     * マッピングするオブジェクトを作成する。
     */
    @Bean
    @DependsOn("dynamoDbEnhancedClient")
    fun keyValueModelTable(dynamoDbEnhancedClient: DynamoDbEnhancedClient): DynamoDbTable<TaskModel> {
        val tableSchema = TableSchema.fromBean(TaskModel::class.java)
        return dynamoDbEnhancedClient.table(properties.dynamoDbTableName, tableSchema)
    }
}
