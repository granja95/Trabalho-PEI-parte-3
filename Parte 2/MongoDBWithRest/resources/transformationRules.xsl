<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:template match="/">
        <g:gestao empresa="BikeOnTrack" 
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                  xmlns:g="http://www.BikeOnTrack.com/Gestao"
                  xsi:schemaLocation="http://www.BikeOnTrack.com/Gestao gestao.xsd"
                  xmlns:v="http://www.BikeOnTrack.com/Vendas"
                  xmlns:c="http://www.BikeOnTrack.com/Cliente"
                  xmlns:d="http://www.BikeOnTrack.com/Datas"
                  xmlns:t="http://www.BikeOnTrack.com/Taxas"
                  xmlns:s="http://www.BikeOnTrack.com/Stock"
                  xmlns:p="http://www.BikeOnTrack.com/Produtos"
                  xmlns:q="http://www.BikeOnTrack.com/Queries"  
                  xmlns:ps="http://www.BikeOnTrack.com/ProductSale" 
                  xmlns:pas="http://www.BikeOnTrack.com/ProductAllSales"        >
            <g:Nome>
                <xsl:value-of select="root/Sales/StoreName"/>
            </g:Nome>
            <q:Queries>
                <q:ProductSale>
                    <ps:ProductTotal>
                        <xsl:for-each select = "root/consulta_t1_1"   >
                            <ps:id>
                                <xsl:value-of select="_id"/>
                            </ps:id>
                            <ps:total>
                                <xsl:value-of select="ProductTotal"/>
                            </ps:total>
                        </xsl:for-each>
                    </ps:ProductTotal>
                    <ps:DifProductTotal>
                        <xsl:for-each select = "root/consulta_t1_2"   >
                            <ps:ReceiptID>
                                <xsl:value-of select="_id"/>
                            </ps:ReceiptID>
                            
                            <ps:total>
                                <xsl:value-of select="DifProductTotal"/>
                            </ps:total>
                        </xsl:for-each>
                    </ps:DifProductTotal>
                    <ps:AvgProductPrice>
                        <xsl:for-each select = "root/consulta_t1_3"   >
                            <ps:id>
                                <xsl:value-of select="_id"/>
                            </ps:id>
                            <ps:total>
                                <xsl:value-of select="AvgProductPrice"/>
                            </ps:total>
                        </xsl:for-each>
                    </ps:AvgProductPrice>
                </q:ProductSale>
                <q:ProductAllSales>
                    <pas:AllProducts>
                        <xsl:for-each select = "root/consulta_t2_1"   >
                            <pas:AllProducts>
                                <xsl:value-of select="AllProducts" />
                            </pas:AllProducts>
                        </xsl:for-each>
                    </pas:AllProducts>
                    <pas:AllDifProducts>
                        <xsl:for-each select = "root/consulta_t2_2"   >
                            <pas:Products>
                                <xsl:value-of select="Products" />
                            </pas:Products>
                        </xsl:for-each>
                    </pas:AllDifProducts>
                    <pas:AllDifClients>
                        <xsl:for-each select = "root/consulta_t2_3"   >
                            <pas:Customer>
                                <xsl:value-of select="Customer" />
                            </pas:Customer>
                        </xsl:for-each>
                    </pas:AllDifClients>
                    <pas:SalesPerClient>
                        <xsl:for-each select = "root/consulta_t2_4"   >
                            <pas:SalesPerClient>
                                <xsl:value-of select="SalesPerClient" />
                            </pas:SalesPerClient>
                        </xsl:for-each>
                    </pas:SalesPerClient>
                    <pas:TotalProductSales>
                        <xsl:for-each select = "root/consulta_t2_5"   >
                            <pas:id>
                                <xsl:value-of select="_id"/>
                            </pas:id>
                            <pas:TotalProductSales>
                                <xsl:value-of select="TotalProductSales"/>
                            </pas:TotalProductSales>
                        </xsl:for-each>
                    </pas:TotalProductSales>
                    <pas:TotalSalesCurrency>
                        <xsl:for-each select = "root/consulta_t2_6"   >
                            <pas:TotalSalesCurrency>
                                <xsl:value-of select="TotalSalesCurrency" />
                            </pas:TotalSalesCurrency>
                        </xsl:for-each>
                    </pas:TotalSalesCurrency>
                </q:ProductAllSales>
                <q:GeneralInfo>
                    <gi:AllProductsSoldPerStore>
                        <xsl:for-each select = "root/consulta_t3_1"   >
                            <gi:id>
                                <xsl:value-of select="_id"/>
                            </gi:id>
                            <gi:AllProductsSoldPerStore>
                                <xsl:value-of select="AllProductsSoldPerStore"/>
                            </gi:AllProductsSoldPerStore>
                        </xsl:for-each>
                    </gi:AllProductsSoldPerStore>
                    <gi:TotalSalesPerStore>
                        <xsl:for-each select = "root/consulta_t3_2"   >
                            <gi:id>
                                <xsl:value-of select="_id"/>
                            </gi:id>
                            <gi:TotalSalesPerStore>
                                <xsl:value-of select="TotalSalesPerStore"/>
                            </gi:TotalSalesPerStore>
                        </xsl:for-each>
                    </gi:TotalSalesPerStore>
                    <gi:AvgProductSalesPerStore>
                        <xsl:for-each select = "root/consulta_t3_3"   >
                            <gi:id>
                                <xsl:value-of select="_id"/>
                            </gi:id>
                            <gi:AvgProductSalesPerStore>
                                <xsl:value-of select="AvgProductSalesPerStore"/>
                            </gi:AvgProductSalesPerStore>
                        </xsl:for-each>
                    </gi:AvgProductSalesPerStore>
                </q:GeneralInfo>
            </Queries>
            <g:datas>
                <d:dataInicio>
                    <xsl:value-of select = "root/Products/SellStartDate"  />
                </d:dataInicio>
                <d:dataFim>
                    <xsl:value-of select = "root/Products/SellEndDate"  />
                </d:dataFim>
                <d:venda>
                    <v:ReceiptID>
                        <xsl:value-of select = "root/Sales/ReceiptID"  />
                    </v:ReceiptID>
                    <v:OrderDate>
                        <xsl:value-of select = "root/Sales/OrderDate"   />
                        
                    </v:OrderDate>
                    <v:cliente>
                        <c:ID>
                            <xsl:value-of select = "root/Sales/Costumer"/>
                        </c:ID>
                    </v:cliente>
                    <xsl:for-each select = "root/Taxas"   >
                        <v:taxas>
                            
                            <t:CurrencyRateID>
                                <xsl:value-of select = "CurrencyRateID"  />
                            </t:CurrencyRateID>
                            <t:CurrencyRateDate>
                                <xsl:value-of select = "CurrencyRateDate"  />
                            </t:CurrencyRateDate>
                            <t:FromCurrencyCode>
                                <xsl:value-of select = "FromCurrencyCode"  />
                            </t:FromCurrencyCode>
                            <t:ToCurrencyCode>
                                <xsl:value-of select = "ToCurrencyCode"  />
                            </t:ToCurrencyCode>
                            <t:RateVal>
                                <xsl:value-of select = "RateVal"  />
                            </t:RateVal>
                            
                        </v:taxas>
                    </xsl:for-each>
                    
                    <v:SubTotal>
                        <xsl:value-of select = "root/Sales/SubTotal"   />
                    </v:SubTotal>
                    <v:TaxAmt>
                        <xsl:value-of select = "root/Sales/TaxAmt"   />
                    </v:TaxAmt>
                    <v:stock>
                        <s:ReceiptLineID>
                            <xsl:value-of select = "root/Sales/ReceiptLineID"  />
                        </s:ReceiptLineID>
                        <s:Order>
                            <xsl:value-of select = "root/Sales/Order"  />
                        </s:Order>
                        <xsl:for-each select = "root/Products"   >
                            <s:produto>
                                <p:ProductID>
                                    <xsl:value-of select = "ProductID"  />
                                </p:ProductID>
                                <p:ProductName>
                                    <xsl:value-of select = "Name"  />
                                </p:ProductName>
                                <p:ProductNumber>
                                    <xsl:value-of select = "ProductNumber"  />
                                </p:ProductNumber>
                                <p:Color>
                                    <xsl:value-of select = "Color"  />
                                </p:Color>
                                <p:ListPrice>
                                    <xsl:value-of select = "ListPrice"  />
                                </p:ListPrice>
                                <p:SellStartDate>
                                    <xsl:value-of select = "SellStartDate"  />
                                </p:SellStartDate>
                                <p:SellEndDate>
                                    <xsl:value-of select = "SellEndDate"  />
                                </p:SellEndDate>
                            </s:produto>
                        </xsl:for-each>
                        
                        <s:UnitPrice></s:UnitPrice>
                    </v:stock>
                    <v:LineTotal>
                        <xsl:value-of select = "root/Sales/LineTotal"   />
                    </v:LineTotal>
                </d:venda>
            </g:datas>
        </g:gestao>
    </xsl:template>
    
</xsl:stylesheet>
