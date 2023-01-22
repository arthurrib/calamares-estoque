import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProdutoFornecedorFormService } from './produto-fornecedor-form.service';
import { ProdutoFornecedorService } from '../service/produto-fornecedor.service';
import { IProdutoFornecedor } from '../produto-fornecedor.model';
import { IProduto } from 'app/entities/produto/produto.model';
import { ProdutoService } from 'app/entities/produto/service/produto.service';
import { IFornecedor } from 'app/entities/fornecedor/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor/service/fornecedor.service';
import { IPedido } from 'app/entities/pedido/pedido.model';
import { PedidoService } from 'app/entities/pedido/service/pedido.service';

import { ProdutoFornecedorUpdateComponent } from './produto-fornecedor-update.component';

describe('ProdutoFornecedor Management Update Component', () => {
  let comp: ProdutoFornecedorUpdateComponent;
  let fixture: ComponentFixture<ProdutoFornecedorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let produtoFornecedorFormService: ProdutoFornecedorFormService;
  let produtoFornecedorService: ProdutoFornecedorService;
  let produtoService: ProdutoService;
  let fornecedorService: FornecedorService;
  let pedidoService: PedidoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProdutoFornecedorUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProdutoFornecedorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProdutoFornecedorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    produtoFornecedorFormService = TestBed.inject(ProdutoFornecedorFormService);
    produtoFornecedorService = TestBed.inject(ProdutoFornecedorService);
    produtoService = TestBed.inject(ProdutoService);
    fornecedorService = TestBed.inject(FornecedorService);
    pedidoService = TestBed.inject(PedidoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call produto query and add missing value', () => {
      const produtoFornecedor: IProdutoFornecedor = { id: 456 };
      const produto: IProduto = { id: 3963 };
      produtoFornecedor.produto = produto;

      const produtoCollection: IProduto[] = [{ id: 43024 }];
      jest.spyOn(produtoService, 'query').mockReturnValue(of(new HttpResponse({ body: produtoCollection })));
      const expectedCollection: IProduto[] = [produto, ...produtoCollection];
      jest.spyOn(produtoService, 'addProdutoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produtoFornecedor });
      comp.ngOnInit();

      expect(produtoService.query).toHaveBeenCalled();
      expect(produtoService.addProdutoToCollectionIfMissing).toHaveBeenCalledWith(produtoCollection, produto);
      expect(comp.produtosCollection).toEqual(expectedCollection);
    });

    it('Should call fornecedor query and add missing value', () => {
      const produtoFornecedor: IProdutoFornecedor = { id: 456 };
      const fornecedor: IFornecedor = { id: 39430 };
      produtoFornecedor.fornecedor = fornecedor;

      const fornecedorCollection: IFornecedor[] = [{ id: 72954 }];
      jest.spyOn(fornecedorService, 'query').mockReturnValue(of(new HttpResponse({ body: fornecedorCollection })));
      const expectedCollection: IFornecedor[] = [fornecedor, ...fornecedorCollection];
      jest.spyOn(fornecedorService, 'addFornecedorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produtoFornecedor });
      comp.ngOnInit();

      expect(fornecedorService.query).toHaveBeenCalled();
      expect(fornecedorService.addFornecedorToCollectionIfMissing).toHaveBeenCalledWith(fornecedorCollection, fornecedor);
      expect(comp.fornecedorsCollection).toEqual(expectedCollection);
    });

    it('Should call Pedido query and add missing value', () => {
      const produtoFornecedor: IProdutoFornecedor = { id: 456 };
      const produtoFornecedor: IPedido = { id: 40302 };
      produtoFornecedor.produtoFornecedor = produtoFornecedor;

      const pedidoCollection: IPedido[] = [{ id: 51326 }];
      jest.spyOn(pedidoService, 'query').mockReturnValue(of(new HttpResponse({ body: pedidoCollection })));
      const additionalPedidos = [produtoFornecedor];
      const expectedCollection: IPedido[] = [...additionalPedidos, ...pedidoCollection];
      jest.spyOn(pedidoService, 'addPedidoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ produtoFornecedor });
      comp.ngOnInit();

      expect(pedidoService.query).toHaveBeenCalled();
      expect(pedidoService.addPedidoToCollectionIfMissing).toHaveBeenCalledWith(
        pedidoCollection,
        ...additionalPedidos.map(expect.objectContaining)
      );
      expect(comp.pedidosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const produtoFornecedor: IProdutoFornecedor = { id: 456 };
      const produto: IProduto = { id: 97765 };
      produtoFornecedor.produto = produto;
      const fornecedor: IFornecedor = { id: 78589 };
      produtoFornecedor.fornecedor = fornecedor;
      const produtoFornecedor: IPedido = { id: 26493 };
      produtoFornecedor.produtoFornecedor = produtoFornecedor;

      activatedRoute.data = of({ produtoFornecedor });
      comp.ngOnInit();

      expect(comp.produtosCollection).toContain(produto);
      expect(comp.fornecedorsCollection).toContain(fornecedor);
      expect(comp.pedidosSharedCollection).toContain(produtoFornecedor);
      expect(comp.produtoFornecedor).toEqual(produtoFornecedor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProdutoFornecedor>>();
      const produtoFornecedor = { id: 123 };
      jest.spyOn(produtoFornecedorFormService, 'getProdutoFornecedor').mockReturnValue(produtoFornecedor);
      jest.spyOn(produtoFornecedorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produtoFornecedor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produtoFornecedor }));
      saveSubject.complete();

      // THEN
      expect(produtoFornecedorFormService.getProdutoFornecedor).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(produtoFornecedorService.update).toHaveBeenCalledWith(expect.objectContaining(produtoFornecedor));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProdutoFornecedor>>();
      const produtoFornecedor = { id: 123 };
      jest.spyOn(produtoFornecedorFormService, 'getProdutoFornecedor').mockReturnValue({ id: null });
      jest.spyOn(produtoFornecedorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produtoFornecedor: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: produtoFornecedor }));
      saveSubject.complete();

      // THEN
      expect(produtoFornecedorFormService.getProdutoFornecedor).toHaveBeenCalled();
      expect(produtoFornecedorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProdutoFornecedor>>();
      const produtoFornecedor = { id: 123 };
      jest.spyOn(produtoFornecedorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ produtoFornecedor });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(produtoFornecedorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProduto', () => {
      it('Should forward to produtoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(produtoService, 'compareProduto');
        comp.compareProduto(entity, entity2);
        expect(produtoService.compareProduto).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFornecedor', () => {
      it('Should forward to fornecedorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fornecedorService, 'compareFornecedor');
        comp.compareFornecedor(entity, entity2);
        expect(fornecedorService.compareFornecedor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePedido', () => {
      it('Should forward to pedidoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pedidoService, 'comparePedido');
        comp.comparePedido(entity, entity2);
        expect(pedidoService.comparePedido).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
